//package com.example.genie_pet_project.network;
//
//
//import com.example.genie_pet_project.model.Login;
//import com.example.genie_pet_project.model.PostModel;
//import com.example.genie_pet_project.model.RegisterModel;
//import com.example.genie_pet_project.model.User;
//
//import io.reactivex.Observable;
//import okhttp3.MultipartBody;
//import okhttp3.ResponseBody;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.Header;
//import retrofit2.http.Multipart;
//import retrofit2.http.POST;
//import retrofit2.http.Part;
//
//public interface NetworkService {
//    @Multipart
//    @POST("pot/upload/")
//    Observable<ResponseBody> uploadFile(@Part MultipartBody.Part file);
//
//    //Tip 받기
//    @GET("top/")
//    Observable<ResponseBody> tipload();
//
//    //견종 ADD
//    @POST("dog/")
//    Observable<ResponseBody> addPostVoditel(@Header("Authorization") String djangoToken, @Body PostModel postModel);
//
//    //로그인
//    @POST("auth/login/")
//    Observable<User> login(@Body Login login);
//
//    //회원가입
//    @POST("auth/register/")
//    Observable<RegisterModel> registrationUser(@Body RegisterModel userModel);
//}