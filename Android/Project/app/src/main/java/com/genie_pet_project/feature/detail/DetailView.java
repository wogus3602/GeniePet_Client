package com.genie_pet_project.feature.detail;

import com.alandwiprasetyo.mvp.models.Item;

/**
 * Created by alandwiprasetyo on 12/27/16.
 */

interface DetailView {

    void showLoading();

    void hideLoading();

    void getDataSuccess(Item item);

    void getDataFail(String message);

    void refreshData();
}
