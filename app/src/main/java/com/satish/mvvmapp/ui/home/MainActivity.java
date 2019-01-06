package com.satish.mvvmapp.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.satish.mvvmapp.R;
import com.satish.mvvmapp.base.BaseActivity;
import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainNavigator {

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        enableToolbarBackNavigation();

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.setNavigator(this);

        initLoader();
    }

    private void initLoader() {
        mViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading)
                showProgress();
            else
                hideProgress();
        });
    }

    @OnClick(R.id.btn_get_items)
    void getMenuItemsClick() {
        mViewModel.getMenuItems();
    }

    @OnClick(R.id.btn_get_movies)
    void onGetMoviesClick() {
        mViewModel.getMovies();
    }

    @Override
    public void onMenuItemsReceived(List<MenuItem> menuItems) {
        Timber.e("onMenuItemsReceived: %s | %d", menuItems, menuItems.size());
    }

    @Override
    public void onMoviesReceived(List<Movie> movies) {
        Timber.e("onMoviesReceived: %s | %d", movies, movies.size());
    }

    @Override
    public void onApiError(Throwable throwable) {
        handleApiError(throwable);
    }
}