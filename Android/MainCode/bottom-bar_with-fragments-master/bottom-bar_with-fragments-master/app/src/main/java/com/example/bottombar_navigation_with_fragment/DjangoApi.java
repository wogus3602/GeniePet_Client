package com.example.bottombar_navigation_with_fragment;

import com.example.bottombar_navigation_with_fragment.model.Login;
import com.example.bottombar_navigation_with_fragment.model.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DjangoApi {
    String root = "http://a59c35f9.ngrok.io/";  // 기본주소
    String DJANGO_SITE = root + "pot/";
    String login_page = root + "auth/login";  //로그인 페이지
    String reg_page = root + "auth/regiter";  // 회원가입 페이지
    String DJANGO_SITE3 = root + "blog/";  //로그인 주소

    @Multipart
    @POST("upload/")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

//    @POST("dog/")
//    Call<ResponseBody> addPostVoditel(@Body PostModel postModel);

    //로그인
    @POST("login/")
    Call<User> login(@Body Login login);

    //회원가입
    @POST("register/")
    Call<User> registrationUser(@Body User userModel);

}