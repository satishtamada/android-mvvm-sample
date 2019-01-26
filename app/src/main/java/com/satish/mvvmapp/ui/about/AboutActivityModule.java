package com.satish.mvvmapp.ui.about;

import android.arch.lifecycle.ViewModelProvider;

import com.satish.mvvmapp.data.AppDatabase;
import com.satish.mvvmapp.di.ViewModelProviderFactory;
import com.satish.mvvmapp.network.WebService;
import com.satish.mvvmapp.ui.home.MainActivityViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AboutActivityModule {
    @Provides
    ViewModelProvider.Factory aboutActivityViewModelProvider(AboutActivityViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    AboutActivityViewModel provideAddAccountViewModel(AppDatabase dataRepository, WebService apiService) {
        return new AboutActivityViewModel(dataRepository, apiService);
    }
}
