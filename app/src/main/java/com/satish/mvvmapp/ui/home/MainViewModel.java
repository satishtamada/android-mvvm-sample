package com.satish.mvvmapp.ui.home;

import android.os.Handler;

import com.satish.mvvmapp.base.BaseViewModel;
import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public void getMenuItems() {
        // fetching from db
        getNavigator().onMenuItemsReceived(getAppDatabase().getMenuItems());

        setIsLoading(true);
        Call<List<MenuItem>> call = getWebClient().getMenu();
        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, final Response<List<MenuItem>> response) {
                setIsLoading(false);
                if (!response.isSuccessful()) {
                    return;
                }
                // storing menu items
                getAppDatabase().insertMenuItems(response.body());

                new Handler().postDelayed(() -> {
                    getNavigator().onMenuItemsReceived(response.body());
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                setIsLoading(false);
                getNavigator().onApiError(t);
            }
        });
    }

    public void getMovies() {
        setIsLoading(true);
        Call<List<Movie>> call = getWebClient().getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                setIsLoading(false);
                if (!response.isSuccessful()) {
                    return;
                }

                getNavigator().onMoviesReceived(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                setIsLoading(false);
            }
        });
    }
}
