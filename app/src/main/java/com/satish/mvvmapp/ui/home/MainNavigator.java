package com.satish.mvvmapp.ui.home;

import com.satish.mvvmapp.base.BaseNavigator;
import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;

import java.util.List;

public interface MainNavigator extends BaseNavigator {
    void onMenuItemsReceived(List<MenuItem> menuItems);
    void onMoviesReceived(List<Movie> movies);
}
