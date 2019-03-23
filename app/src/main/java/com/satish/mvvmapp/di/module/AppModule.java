package com.satish.mvvmapp.di.module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.satish.mvvmapp.BuildConfig;
import com.satish.mvvmapp.data.AppDatabase;
import com.satish.mvvmapp.data.AppPref;
import com.satish.mvvmapp.data.Constants;
import com.satish.mvvmapp.di.PreferenceInfo;
import com.satish.mvvmapp.network.WebService;
import com.satish.mvvmapp.utils.ConnectivityInterceptor;
import com.satish.mvvmapp.utils.UnauthorizedInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    /*@Provides
    @Singleton
    AppPrefHelper provideAppPrefHelper(AppPrefHelper appPrefHelper) {
        return appPrefHelper;
    }

    @Provides
    @Singleton
    AppDatabaseHelper provideAppDatabaseHelper(AppDatabaseHelper appDatabaseHelper) {
        return appDatabaseHelper;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return new AppDatabase();
    }

    @Provides
    @Singleton
    DataRepository provideDataRepository(AppDatabase database, AppPref appPref) {
        return new DataRepository(database, appPref);
    }*/

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return new AppDatabase();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppPref.PREF_NAME;
    }

    @Provides
    @Singleton
    AppPref provideAppPrefs() {
        return AppPref.getInstance();
    }

    @Provides
    @Singleton
    WebService providesApiService(Retrofit retrofit) {
        return retrofit.create(WebService.class);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Context context, AppPref appPref) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.MINUTES);
        builder.hostnameVerifier((hostname, session) -> true);

        if (BuildConfig.DEBUG) {
            // Added interceptor for http logging
            builder.addInterceptor(httpLoggingInterceptor);
        }

        builder.addInterceptor(new UnauthorizedInterceptor());
        builder.addInterceptor(new ConnectivityInterceptor(context));

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json");

            String userToken = appPref.getAuthToken();
            if (!TextUtils.isEmpty(userToken)) {
                requestBuilder.addHeader("Authorization", "Bearer " + userToken);
            }

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return builder.build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
