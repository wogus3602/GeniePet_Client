package com.example.myapplication.Django;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.example.myapplication.DataManager;
import com.example.myapplication.DjangoAdapter.LoginAdapter;
import com.example.myapplication.DjangoAdapter.PostModel;
//import com.example.myapplication.MainActivity;
import com.example.myapplication.DjangoAdapter.PostsAdapter;
import com.example.myapplication.DjangoAdapter.UserAdapter;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

public class DjangoREST {
    private Retrofit retrofit;
    private DjangoApi postApi;
    public String MyResult;

    public void setMyResult(String myResult) {
        MyResult = myResult;
    }

    //이미지 Django에 올리기
    public void uploadFoto(String storage) {

        retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postApi= retrofit.create(DjangoApi.class);
        String image_path = storage;
        File imageFile = new File(image_path);  // File 이미지 경로 저장

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);

        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", imageFile.getName(), requestBody);

        Call<ResponseBody> call = postApi.uploadFile(multiPartBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    MyResult = response.body().string();
                    setMyResult(MyResult);
                    Log.d("MyResult", "" +MyResult);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("fail", "fail" + t);
            }
        });
    }

    public String getMyResult() {
        return MyResult;
    }

    //정보 올리기
    public void AddPostServer(String name, String species, String age) {

        retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postApi= retrofit.create(DjangoApi.class);

        PostModel postModel = new PostModel(
                name,
                species,
                age
        );
        String token = "JWT " +"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxNSwidXNlcm5hbWUiOiJ0ZXN0NSIsImV4cCI6MTU3MTIzNTg4MiwiZW1haWwiOiJ0ZXN0QHRlc3QudGVzdDExNSJ9.zxmTvW5VPs14p4PoWpnD6jPifXJK4i2SHo-zn0qasdU";
        Call<ResponseBody> comment = postApi.addPostVoditel(token,postModel);
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


    public void login(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE3)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();


        DjangoApi loginApi = retrofit.create(DjangoApi.class);

        String str_username      =  "Park";
        String str_password      =  "12345";

        LoginAdapter loginAdapter = new LoginAdapter(str_username, str_password);

        Call<UserAdapter> call = loginApi.login(loginAdapter);

        call.enqueue(new Callback<UserAdapter>() {

            @Override
            public void onResponse(Call<UserAdapter> call, Response<UserAdapter> response) {
                if(response.isSuccessful()){


                    if (response.body() != null) {

                        String token = response.body().getToken();
                        //Toast.makeText(MainActivity.mContext.getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        showPost(token);
                    }

                }else {
                    Log.d("fail","fail" + response);
                }
            }

            @Override
            public void onFailure(Call<UserAdapter> call, Throwable t) {
                Log.d("fail","fail" + t);
            }
        });
    }

    private void showPost(String token) {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE3)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();


        DjangoApi login= retrofit.create(DjangoApi.class);

        String Token_request = "Token " + token;

        Call<List<PostsAdapter>> call = login.getPost(Token_request);

        call.enqueue(new Callback<List<PostsAdapter>>() {
            @Override
            public void onResponse(Call<List<PostsAdapter>> call, Response<List<PostsAdapter>> response) {

                if(response.isSuccessful()){

                    if (response.body() != null) {

                        List<PostsAdapter> heroList = response.body();

                        for(PostsAdapter h:heroList){
                            String title = h.getTitle();
                            //Toast.makeText(MainActivity.mContext.getApplicationContext(), title, Toast.LENGTH_SHORT).show();

                            String text = h.getText();
                            //Toast.makeText(MainActivity.mContext.getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                            String species = h.getSpecies();
                        }

                    }

                }else {
                    Log.d("fail", "fail");
                }

            }

            @Override
            public void onFailure(Call<List<PostsAdapter>> call, Throwable t) {
                Log.d("fail","fail");
            }

        });

    }
}
