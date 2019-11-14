package com.example.bottombar_navigation_with_fragment.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottombar_navigation_with_fragment.Adapter.ListViewAdapter;
import com.example.bottombar_navigation_with_fragment.DjangoApi;

import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.SaveSharedPreference;
import com.example.bottombar_navigation_with_fragment.model.ListVO;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreListActivity extends AppCompatActivity{
    public static ListViewAdapter adapter;
    public static ListView listView;
    public static StoreListActivity mStoreListActivity;
    @BindView(R.id.isLogin)
    TextView mTextview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list);
        mStoreListActivity = this;
        ButterKnife.bind(this);
        isLoginStatus();
        new ParseTask().execute();

    }

    public static StoreListActivity getInstance() {
        if(mStoreListActivity == null){
            mStoreListActivity = new StoreListActivity();
        }
        return mStoreListActivity;
    }

    public void Click(ListVO listItem){
        Log.d("aaaaaaaaaaaaaaaaaa","");
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("object",listItem);
        startActivity(intent);
    }


    protected void isLoginStatus(){
        if(!SaveSharedPreference.getLogged(MainActivity.getInstance())){
            mTextview.setText("로그인을 해주세요");
        }else{
            if(SaveSharedPreference.getSpecie(MainActivity.getInstance())==""){
                String strColor = "#FF0004";
                mTextview.setTextColor(Color.parseColor(strColor));
                mTextview.setText("프로필 등록을 해주시면 정확한 추천을 해드리겠습니다.");
            }else {
                mTextview.setText(SaveSharedPreference.getSpecie(MainActivity.getInstance()) + " 종에게 맞춤 추천 상품입니다.");
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
            try{
                String site_url_json = DjangoApi.root+"feed/";
                URL url = new URL(site_url_json);

                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestMethod("GET");
                urlconnection.connect();

                InputStream inputStream = urlconnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e){
                e.printStackTrace();
            }
            return resultJson;
        }

        //쓰레드가 시작하기 전에 수행할 작업(Main Thread)
        protected void onPostExecute(String strJson){
            super.onPostExecute(strJson);

            adapter = new ListViewAdapter();
            listView = findViewById(R.id.List_view);
            listView.setAdapter(adapter);

            try {
                JSONArray jArray = new JSONArray(strJson);

                Log.d("FOR_LOG", ""+jArray);
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
