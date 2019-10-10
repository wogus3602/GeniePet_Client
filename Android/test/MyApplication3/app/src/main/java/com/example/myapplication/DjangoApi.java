package com.example.myapplication;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Body;


public interface DjangoApi {

    String DJANGO_SITE = "http://121.163.43.131:8000/image/";
    String DJANGO_SITE2 = "http://121.163.43.131:8000/post/";

    @Multipart
    @POST("upload/")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    @POST("create/")
    Call<ResponseBody> addPostVoditel(@Body PostModel postModel);

}