package com.satish.mvvmapp.base;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.satish.mvvmapp.data.AppDatabase;
import com.satish.mvvmapp.network.WebService;

import java.lang.ref.WeakReference;

public class BaseViewModel<T> extends ViewModel {
    private WebService webClient;
    private AppDatabase appDatabase;
    private WeakReference<T> mNavigator;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public BaseViewModel(AppDatabase appDatabase, WebService webService) {
        this.appDatabase = appDatabase;
        this.webClient = webService;
    }

    public WebService getWebClient() {
        return webClient;
    }

    public AppDatabase getAppDatabase() {
        if (appDatabase == null) {
            appDatabase = new AppDatabase();
        }
        return appDatabase;
    }

    public void setNavigator(T navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public T getNavigator() {
        return mNavigator.get();
    }

    public LiveData<Boolean> getLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.setValue(isLoading);
    }
}
