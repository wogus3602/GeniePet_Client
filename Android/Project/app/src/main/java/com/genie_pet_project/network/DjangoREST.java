package com.genie_pet_project.network;

import android.util.Log;

import com.genie_pet_project.MainActivity;
import com.genie_pet_project.SaveSharedPreference;
import com.genie_pet_project.models.PostModel;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.myapplication.MainActivity;

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
                .baseUrl(DjangoApi.ImageUpload)
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
                    Log.d("",""+response.body());
                    SaveSharedPreference.setSpecie(MainActivity.getInstance(),MyResult);

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
                .baseUrl(DjangoApi.root)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postApi= retrofit.create(DjangoApi.class);

        PostModel postModel = new PostModel(
                name,
                species,
                age
        );

        String token = "JWT "+ SaveSharedPreference.getToken(MainActivity.getInstance());
        Log.d("adsada",""+token);
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



    //정보 올리기
    public void Tip() {

        retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.root)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postApi= retrofit.create(DjangoApi.class);


        Call<ResponseBody> tip = postApi.tipload();
        tip.enqueue(new Callback<ResponseBody>() {
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


//
//    public void login(){
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(DjangoApi.DJANGO_SITE3)
//                .addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit = builder.build();
//
//
//        DjangoApi loginApi = retrofit.create(DjangoApi.class);
//
//        String str_username      =  "Park";
//        String str_password      =  "12345";
//
//        LoginAdapter loginAdapter = new LoginAdapter(str_username, str_password);
//
//        Call<UserAdapter> call = loginApi.login(loginAdapter);
//
//        call.enqueue(new Callback<UserAdapter>() {
//
//            @Override
//            public void onResponse(Call<UserAdapter> call, Response<UserAdapter> response) {
//                if(response.isSuccessful()){
//
//
//                    if (response.body() != null) {
//
//                        String token = response.body().getToken();
//                        //Toast.makeText(MainActivity.mContext.getApplicationContext(), token, Toast.LENGTH_SHORT).show();
//                        showPost(token);
//                    }
//
//                }else {
//                    Log.d("fail","fail" + response);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserAdapter> call, Throwable t) {
//                Log.d("fail","fail" + t);
//            }
//        });
//    }
//
//    private void showPost(String token) {
//
//
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(DjangoApi.DJANGO_SITE3)
//                .addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit = builder.build();
//
//
//        DjangoApi login= retrofit.create(DjangoApi.class);
//
//        String Token_request = "Token " + token;
//
//        Call<List<PostsAdapter>> call = login.getPost(Token_request);
//
//        call.enqueue(new Callback<List<PostsAdapter>>() {
//            @Override
//            public void onResponse(Call<List<PostsAdapter>> call, Response<List<PostsAdapter>> response) {
//
//                if(response.isSuccessful()){
//
//                    if (response.body() != null) {
//
//                        List<PostsAdapter> heroList = response.body();
//
//                        for(PostsAdapter h:heroList){
//                            String title = h.getTitle();
//                            //Toast.makeText(MainActivity.mContext.getApplicationContext(), title, Toast.LENGTH_SHORT).show();
//
//                            String text = h.getText();
//                            //Toast.makeText(MainActivity.mContext.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//
//                            String species = h.getSpecies();
//                        }
//
//                    }
//
//                }else {
//                    Log.d("fail", "fail");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<PostsAdapter>> call, Throwable t) {
//                Log.d("fail","fail");
//            }
//
//        });
//
//    }
}
