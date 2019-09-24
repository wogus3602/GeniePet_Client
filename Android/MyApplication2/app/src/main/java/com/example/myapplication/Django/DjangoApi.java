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
import retrofit2.http.Query;


public interface DjangoApi {
    String Ipaddress = "https://e0d51fc2.ngrok.io/";
    String DJANGO_SITE = Ipaddress + "pot/";
    String DJANGO_SITE2 = Ipaddress + "post/";
    String DJANGO_SITE3 = Ipaddress + "blog/";  //로그인 주소

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

    @GET ("comments")
    Call<ResponseBody>getComment(@Query("result") String result);

}