package com.satish.mvvmapp.di.component;

import android.app.Application;

import com.satish.mvvmapp.app.MyApplication;
import com.satish.mvvmapp.di.builder.ActivityBuilder;
import com.satish.mvvmapp.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MyApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

