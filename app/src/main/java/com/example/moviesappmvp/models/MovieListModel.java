package com.example.moviesappmvp.models;

import android.util.Log;

import com.example.moviesappmvp.api.ApiClient;
import com.example.moviesappmvp.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListModel {
    private final String TAG = "MovieListModel";

    private String apiKey = "c6141ba11d0bb71fbe3ec33b08c8125a";
    private ApiInterface apiInterface;

    public MovieListModel() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getPopularMovieList(final OnFinishedListener onFinishedListener, int pageNo) {
        Call<MovieList> call = apiInterface.getPopularMovies(apiKey, pageNo);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e(TAG, "Error " + t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

    public void getNowPlayingMovieList(final OnFinishedListener onFinishedListener, int pageNo) {
        Call<MovieList> call = apiInterface.getNowPlayingMovies(apiKey, pageNo);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e(TAG, "Error " + t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

    public interface OnFinishedListener {
        void onFinished(List<Movie> movieArrayList);

        void onFailure(Throwable t);
    }

}
