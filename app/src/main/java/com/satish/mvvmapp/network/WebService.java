package com.satish.mvvmapp.network;

import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("menu.json")
    Call<List<MenuItem>> getMenu();

    @GET("movies_2017.json")
    Call<List<Movie>> getMovies();
}
