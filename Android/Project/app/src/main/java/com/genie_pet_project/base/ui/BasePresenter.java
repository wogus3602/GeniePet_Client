package com.genie_pet_project.base.ui;

import android.util.Log;

import com.genie_pet_project.network.NetworkClient;
import com.genie_pet_project.network.NetworkStores;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V> {
    public V view;
    protected NetworkStores apiStores;
    private CompositeSubscription compositeSubscription;
    private Subscriber subscriber;

    public void attachView(V view) {
        this.view = view;
        apiStores = NetworkClient.getRetrofit().create(NetworkStores.class);
    }

    public void dettachView() {
        this.view = null;
        onUnsubscribe();
    }

    public void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
            Log.e("TAG", "onUnsubscribe: ");
        }
    }

    protected void addSubscribe(Observable observable, Subscriber subscriber) {
        this.subscriber = subscriber;
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void stop() {
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
    }
}
