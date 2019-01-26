package com.satish.mvvmapp.di.builder;

import com.satish.mvvmapp.ui.about.AboutActivity;
import com.satish.mvvmapp.ui.about.AboutActivityModule;
import com.satish.mvvmapp.ui.home.MainActivity;
import com.satish.mvvmapp.ui.home.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainctivity();

    @ContributesAndroidInjector(modules = AboutActivityModule.class)
    abstract AboutActivity bindAboutctivity();
}
