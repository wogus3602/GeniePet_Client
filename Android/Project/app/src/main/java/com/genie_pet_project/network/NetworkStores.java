package com.genie_pet_project.network;

import com.genie_pet_project.models.Login;
import com.genie_pet_project.models.PostModel;
import com.genie_pet_project.models.RegisterModel;
import com.genie_pet_project.models.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alandwiprasetyo on 11/22/16.
 */

public interface NetworkStores {
    @Multipart
    @POST("upload/")
    Observable<ResponseBody> uploadFile(@Part MultipartBody.Part file);

    //Tip 받기
    @GET("top/")
    Observable<ResponseBody> tipload();

    //견종 ADD
    @POST("dog/")
    Observable<ResponseBody> addPostVoditel(@Header("Authorization") String djangoToken, @Body PostModel postModel);

    //로그인
    @POST("login/")
    Observable<User> login(@Body Login login);

    //회원가입
    @POST("register/")
    Observable<RegisterModel> registrationUser(@Body RegisterModel userModel);

}