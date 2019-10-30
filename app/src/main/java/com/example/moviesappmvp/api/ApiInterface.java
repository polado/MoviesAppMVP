package com.example.moviesappmvp.api;

import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.models.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("movie/now_playing")
    Call<MovieList> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int PageNo);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}