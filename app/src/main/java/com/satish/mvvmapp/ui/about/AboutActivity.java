package com.satish.mvvmapp.ui.about;

import android.os.Bundle;

import com.satish.mvvmapp.R;
import com.satish.mvvmapp.base.BaseActivity;
import com.satish.mvvmapp.data.AppPref;

import javax.inject.Inject;

import timber.log.Timber;

public class AboutActivity extends BaseActivity implements AboutActivityNavigator {

    @Inject
    AppPref appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Timber.e("AppPref: %s", appPref);
    }

    @Override
    public void onApiError(Throwable throwable) {
        // TODO handle error
    }
}
