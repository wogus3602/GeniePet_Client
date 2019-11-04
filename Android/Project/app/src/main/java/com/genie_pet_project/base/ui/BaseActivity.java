package com.genie_pet_project.base.ui;

import android.app.Activity;
import retrofit2.Call;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by alandwiprasetyo on 11/22/16.
 */

public class BaseActivity extends AppCompatActivity {

    public Activity activity;
    CompositeSubscription compositeSubscription;
    List<Call> calls;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        activity = this;
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onCancelled();
        onUnsubscribe();
    }

    private void onCancelled() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
            }
        }
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
