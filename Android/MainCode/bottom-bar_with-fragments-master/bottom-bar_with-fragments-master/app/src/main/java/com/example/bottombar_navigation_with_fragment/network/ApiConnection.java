package com.example.bottombar_navigation_with_fragment.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {
    private static String BASE_URL = "http://84a1c1d8.ngrok.io/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    // 싱글톤
    private static final ApiConnection INSTANCE = new ApiConnection();
    public static ApiConnection getInstance() {
        return INSTANCE;
    }
    public NetworkService getRetrofitService() {
        return retrofit.create(NetworkService.class);
    }
}