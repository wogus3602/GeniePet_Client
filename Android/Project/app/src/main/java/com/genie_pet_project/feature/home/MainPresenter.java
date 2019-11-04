package com.genie_pet_project.feature.home;

import android.app.Activity;
import android.content.Intent;

import com.genie_pet_project.base.ui.BasePresenter;

/**
 * Created by alandwiprasetyo on 11/22/16.
 */

class MainPresenter extends BasePresenter<MainView> {

    MainPresenter(MainView view) {
        super.attachView(view);
    }

    void loadData(String key) {
        view.showLoading();
        addSubscribe(apiStores.getTopBooks(key), new NetworkCallback<Books>() {
            @Override
            public void onSuccess(Books model) {
                view.getDataSuccess(model);
            }

            @Override
            public void onFailure(String message) {
                view.getDataFail(message);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    void getItem(Item item, Activity activity) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("id", item.getId());
        view.moveToDetail(intent);
    }
}