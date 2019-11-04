package com.genie_pet_project.network;


import android.util.Log;

import com.genie_pet_project.MainActivity;
import com.genie_pet_project.SaveSharedPreference;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alandwiprasetyo on 11/22/16.
 */

public class NetworkClient {
    private static String root = "http://84a1c1d8.ngrok.io/";  // 기본주소
    private static String ImageUpload = root + "pot/";
    private static String LoginPage = root + "auth/";  //로그인 페이지
    private static String RegisterPage = root + "auth/";  // 회원가입 페이지
    private static String TipPage = root;

    private static Retrofit retrofit;

    //이미지 Django에 올리기
    public static uploadPhoto(String storage) {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ImageUpload)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

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


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}