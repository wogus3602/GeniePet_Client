package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//JSON 형식 받기
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//listview
import java.util.HashMap;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private DjangoApi postApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFoto();
            }
        };

        View.OnClickListener addpost = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPostServer();
            }
        };

        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        };

        button1.setOnClickListener(listener);

        button2.setOnClickListener(addpost);

        button3.setOnClickListener(login);


        new ParseTask().execute();   // Django -> android GET형식으로 받기 실행
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
                String site_url_json = "http://121.163.43.131:8000/feed";
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

            final ListView lView = findViewById(R.id.lvMain);

            String[] from = {"name_item", "price", "rating"};
            int[] to = {R.id.name_item, R.id.price, R.id.rating};
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String,String> hashMap;

            try {
                JSONArray jArray = new JSONArray(strJson);
//                JSONObject json = new JSONObject(strJson);
//                JsonArray jArray = json.getJSONObject("");
                //JSONArray jArray = json.getJSONArray("");
                Log.d("FOR_LOG", ""+jArray);
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject friend = jArray.getJSONObject(i);

                    String nameOS = friend.getString("name");
                    String price = friend.getString("price");
                    String rating = friend.getString("rating");

                    Log.d("FOR_LOG", nameOS);

                    hashMap = new HashMap<String, String>();
                    hashMap.put("name_item", "" + nameOS);
                    hashMap.put("price", "" + price);
                    hashMap.put("rating", "" + rating);
                    arrayList.add(hashMap);
                }

                final SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, arrayList, R.layout.item, from , to);
                lView.setAdapter(adapter);
//                JSONArray jsonarray = new JSONArray(strJson);
//                JSONObject jsonobj = jsonarray.getJSONObject(0);
//                Log.d("111111111",""+jsonobj.names());
//
//
//                String result_json_text = jsonobj.getString("rating");
//                Log.d("FOR_LOG", result_json_text);
//
//                TextView textView = findViewById(R.id.showtext);
//                textView.setText(result_json_text);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




    private void uploadFoto() {
        retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postApi= retrofit.create(DjangoApi.class);

        String image_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/IMG_20190825_155404.jpg";
        File imageFile = new File(image_path);  // File 이미지 경로 저장

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);

        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", imageFile.getName(), requestBody);

        Call<ResponseBody> call = postApi.uploadFile(multiPartBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("good", "good" +response);
                Log.d("body", "good"+ response.body());
                Log.d("message", "good"+ response.message());
                Log.d("code", "good"+ response.code());
                Log.d("headers", "good"+ response.headers());
                Log.d("headers", "good"+ response.raw());
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("fail", "fail" + t);
            }
        });


    }


    public void AddPostServer() {

        retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postApi= retrofit.create(DjangoApi.class);

        PostModel postModel = new PostModel(
                "android title",
                "android text"
        );

        Call<ResponseBody> comment = postApi.addPostVoditel(postModel);
        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("ResponseBody", "" + response.raw());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("fail", "fail" + t);
            }
        });
    }


    private void login(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginApi.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();


        LoginApi loginApi = retrofit.create(LoginApi.class);

        String str_username      =  "Park";
        String str_password      =  "12345";

        Login login = new Login(str_username, str_password);

        Call<User> call = loginApi.login(login);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){


                    if (response.body() != null) {

                        String token = response.body().getToken();
                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        showPost(token);
                    }

                }else {
                    Log.d("fail","fail" + response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("fail","fail" + t);
            }
        });
    }

    private void showPost(String token) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginApi.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();


        LoginApi login= retrofit.create(LoginApi.class);

        String Token_request = "Token " + token;

        Call<List<Posts>> call = login.getPost(Token_request);

        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if(response.isSuccessful()){

                    if (response.body() != null) {

                        List<Posts> heroList = response.body();

                        for(Posts h:heroList){

                            String title = h.getTitle();
                            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

                            String text = h.getText();
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Log.d("fail","fail");
            }

        });

    }
}
