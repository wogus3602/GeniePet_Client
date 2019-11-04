package com.genie_pet_project.feature.detail;

import com.genie_pet_project.base.ui.BasePresenter;
import com.genie_pet_project.network.NetworkCallback;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;


public class DetailPresenter extends BasePresenter<DetailView> {

    DetailPresenter(DetailView view) {
        super.attachView(view);
    }

    void loadData(String storage) {
        String image_path = storage;
        File imageFile = new File(image_path);  // File 이미지 경로 저장

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), imageFile);

        MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", imageFile.getName(), requestBody);

        view.showLoading();
        addSubscribe(apiStores.uploadFile(multiPartBody), new NetworkCallback<ResponseBody>() {

            @Override
            public void onSuccess(ResponseBody model) {

            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

}