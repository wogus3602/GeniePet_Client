package com.example.myapplication.Django;

import com.example.myapplication.DjangoAdapter.LoginAdapter;
import com.example.myapplication.DjangoAdapter.PostModel;
import com.example.myapplication.DjangoAdapter.PostsAdapter;
import com.example.myapplication.DjangoAdapter.UserAdapter;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Body;


public interface DjangoApi {

    String DJANGO_SITE = "http://220.79.163.115:8000/image/";
    String DJANGO_SITE2 = "http://220.79.163.115:8000/post/";
    String DJANGO_SITE3 = "http://220.79.163.115:8000/blog/";  //로그인 주소

    @Multipart
    @POST("upload/")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    @POST("create/")
    Call<ResponseBody> addPostVoditel(@Body PostModel postModel);

    //로그인 부분
    @POST("api-token-auth/")
    Call<UserAdapter> login(@Body LoginAdapter loginAdapter);

    @GET("post/")
    Call<List<PostsAdapter>> getPost(@Header("Authorization")  String djangoToken);

}