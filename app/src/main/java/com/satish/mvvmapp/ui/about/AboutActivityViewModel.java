package com.satish.mvvmapp.ui.about;

import com.satish.mvvmapp.base.BaseViewModel;
import com.satish.mvvmapp.data.AppDatabase;
import com.satish.mvvmapp.network.WebService;

public class AboutActivityViewModel extends BaseViewModel<AboutActivityNavigator> {
    public AboutActivityViewModel(AppDatabase appDatabase, WebService webService) {
        super(appDatabase, webService);
    }

    public void getAbout(String url) {

    }
}
