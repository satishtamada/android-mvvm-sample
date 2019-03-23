package com.satish.mvvmapp.network;

import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("menu.json")
    Single<List<MenuItem>> getMenu();

    @GET("movies_2017.json")
    Single<List<Movie>> getMovies();
}
