package com.example.genie_pet_project.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;


import com.example.genie_pet_project.Adapter.StoreListViewAdapter;
import com.example.genie_pet_project.DataManager;
import com.example.genie_pet_project.R;
import com.example.genie_pet_project.SaveSharedPreference;
import com.example.genie_pet_project.model.StoreList;
import com.example.genie_pet_project.network.DjangoApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreListActivity extends AppCompatActivity{
    public static StoreListViewAdapter adapter;
    public static ListView listView;
    public static StoreListActivity mStoreListActivity;
    @BindView(R.id.isLogin)
    TextView mTextview;
    TextView textCartItemCount;
    String getintent;
    String getintentpos;
    int mCartItemCount = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list);
        mStoreListActivity = this;
        ButterKnife.bind(this);
        isLoginStatus();

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        AppbarExcute();


        new ParseTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.action_cart: {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void AppbarExcute(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCartItemCount = DataManager.getInstance().getArray().size();
        setupBadge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem mMenuItem = menu.findItem(R.id.action_cart);
        View actionView = MenuItemCompat.getActionView(mMenuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);
        mCartItemCount = DataManager.getInstance().getArray().size();
        setupBadge();

        actionView.setOnClickListener(v -> onOptionsItemSelected(mMenuItem));

        return true;
    }


    private void setupBadge() {
        if (textCartItemCount != null) {
            if(mCartItemCount < 0) return;
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public static StoreListActivity getInstance() {
        if(mStoreListActivity == null){
            mStoreListActivity = new StoreListActivity();
        }
        return mStoreListActivity;
    }

    public void Click(StoreList listItem){
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("object",listItem);
        startActivity(intent);
    }


    protected void isLoginStatus(){
        boolean check = false;
        if(!SaveSharedPreference.getLogged(MainActivity.getInstance())){
            mTextview.setText("로그인을 해주세요");
            check = false;
        }else{
            check = true;
            if(SaveSharedPreference.getSpecie(MainActivity.getInstance())==""){
                String strColor = "#FF0004";
                mTextview.setTextColor(Color.parseColor(strColor));
                mTextview.setText("프로필 등록을 해주시면 정확한 추천을 해드리겠습니다.");
            }else {
                mTextview.setText(SaveSharedPreference.getDogName(MainActivity.getInstance()) + " 에게 맞춤 추천 상품입니다.");
            }
        }
        if(check){
            getintent = getIntent().getStringExtra("category");
        }else{
            getintentpos=getIntent().getStringExtra("categoryPos");
            Log.d("11111",""+getintentpos);
            if(getintentpos.equals("6")){
                getintent = "feed/";
            }else if(getintentpos.equals("7")){
                getintent = "snack/";
            }else if(getintentpos.equals("8")){
                getintent = "shampoo/";
            }
        }
    }

    //멀티 쓰레드
    private class ParseTask extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlconnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        //쓰레드가 수행할 작업(Generated Thread)
        @Override
        protected String doInBackground(Void... Params) {
            boolean flag = true;
            try{
                String site_url_json;
                if(SaveSharedPreference.getSpecie(MainActivity.getInstance())=="") {
                    site_url_json = DjangoApi.root + getintent;
                    flag = true;
                }
                else{
                    site_url_json = DjangoApi.root + getintent;
                    flag = false;
                }
                URL url = new URL(site_url_json);
                if(flag){
                    urlconnection = (HttpURLConnection) url.openConnection();
                    urlconnection.setRequestMethod("GET");
                    urlconnection.connect();
                }else{
                    String body = "user_dog=john";

                    urlconnection = (HttpURLConnection) url.openConnection();
                    urlconnection.setRequestMethod("POST");
                    urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlconnection.setDoOutput(true);
                    urlconnection.setDoInput(true);
                    OutputStream os = urlconnection.getOutputStream();
                    os.write(body.getBytes());
                    os.flush();
                    os.close();
                }

                InputStream inputStream = urlconnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                resultJson = buffer.toString();

                urlconnection.disconnect();
            } catch (Exception e){
                e.printStackTrace();
            }
            return resultJson;
        }

        //쓰레드가 시작하기 전에 수행할 작업(Main Thread)
        protected void onPostExecute(String strJson){
            super.onPostExecute(strJson);

            adapter = new StoreListViewAdapter();
            listView = findViewById(R.id.List_view);
            listView.setAdapter(adapter);

            try {
                JSONArray jArray = new JSONArray(strJson);

                for(int i = 0; i < jArray.length(); i++){
                    JSONObject friend = jArray.getJSONObject(i);
                    String img = friend.getString("image");
                    String title = friend.getString("name");
                    String context = friend.getString("text");
                    String price = friend.getString("price");
                    adapter.addVO(""+(i+1),img,title,context,price);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
