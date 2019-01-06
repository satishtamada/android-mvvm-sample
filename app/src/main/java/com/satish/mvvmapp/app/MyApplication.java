package com.satish.mvvmapp.app;

import android.app.Application;

import com.satish.mvvmapp.BuildConfig;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        plantTimber();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("mvvmapp.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
