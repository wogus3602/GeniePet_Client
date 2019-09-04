package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApi {

    String DJANGO_SITE = "http://121.163.43.131:8000/blog/";

    @POST("api-token-auth/")
    Call<User> login(@Body Login login);

    @GET("post/")
    Call<List<Posts>> getPost(@Header("Authorization")  String djangoToken);

}