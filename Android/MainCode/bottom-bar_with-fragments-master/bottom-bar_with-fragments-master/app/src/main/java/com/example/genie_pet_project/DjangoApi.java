package com.example.genie_pet_project;

import com.example.genie_pet_project.model.PostModel;
import com.example.genie_pet_project.model.Login;
import com.example.genie_pet_project.model.RegisterModel;
import com.example.genie_pet_project.model.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DjangoApi {
    String root = "http://0fd950c8.ngrok.io/";  // 기본주소
    String ImageUpload = root + "pot/";
    String LoginPage = root + "auth/";  //로그인 페이지
    String RegisterPage = root + "auth/";  // 회원가입 페이지
    String TipPage = root;

    @Multipart
    @POST("upload/")
   Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    //Tip 받기
    @GET("tip/")
    Call<ResponseBody> tipload();

    //견종 ADD
    @POST("dog/")
    Call<ResponseBody> addPostVoditel(@Header("Authorization") String djangoToken, @Body PostModel postModel);

    //로그인
    @POST("login/")
    Call<User> login(@Body Login login);

    //회원가입
    @POST("register/")
    Call<RegisterModel> registrationUser(@Body RegisterModel userModel);

}