package com.example.genie_pet_project.network;

import android.util.Log;

import com.example.genie_pet_project.Activity.MainActivity;
import com.example.genie_pet_project.SaveSharedPreference;
import com.example.genie_pet_project.model.PostModel;

import java.io.File;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    public String tipText;

    public void setMyResult(String myResult) {
        MyResult = myResult;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

//    public void aa(String storage){
//        String image_path = storage;
//        File imageFile = new File(image_path);  // File 이미지 경로 저장
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);
//
//        MultipartBody.Part multiPartBody = MultipartBody.Part
//                .createFormData("model_pic", imageFile.getName(), requestBody);
//
//        ApiConnection.getInstance().getRetrofitService()
//                .uploadFile(multiPartBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(githubUser -> {
//                            Log.d("Observable Data","Observable Data :" + githubUser);
//                        }, throwable -> {
//                        }
//
//                );
//    }

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
                    SaveSharedPreference.setSpecie(MainActivity.getInstance(),MyResult);
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

        String token = "JWT "+SaveSharedPreference.getToken(MainActivity.getInstance());
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
                try {
                    if(response.isSuccessful())
                    MainActivity.getInstance().tipText = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("fail", "fail" + t);
            }
        });
    }

}
