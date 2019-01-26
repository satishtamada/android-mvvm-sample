package com.satish.mvvmapp.ui.home;

import android.arch.lifecycle.ViewModelProvider;

import com.satish.mvvmapp.data.AppDatabase;
import com.satish.mvvmapp.di.ViewModelProviderFactory;
import com.satish.mvvmapp.network.WebService;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    ViewModelProvider.Factory mainActivityViewModelProvider(MainActivityViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    MainActivityViewModel provideAddAccountViewModel(AppDatabase dataRepository, WebService apiService) {
        return new MainActivityViewModel(dataRepository, apiService);
    }
}
