package com.satish.mvvmapp.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;

import com.satish.mvvmapp.R;
import com.satish.mvvmapp.base.BaseActivity;
import com.satish.mvvmapp.data.AppPref;
import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;
import com.satish.mvvmapp.ui.about.AboutActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainNavigator {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    private MainActivityViewModel mViewModel;

    @Inject
    AppPref appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        enableToolbarBackNavigation();
        appPref.saveAuthToken("ALJLFKSJLFJLJLKJKLF");
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel.class);
        mViewModel.setNavigator(this);
        initLoader();

        mViewModel.loadAnimals();
        mViewModel.syncMenu();
    }

    private void initLoader() {
        mViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading)
                showProgress();
            else
                hideProgress();
        });
    }

    @OnClick(R.id.btn_about)
    void onAboutClick() {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}