package com.example.moviesappmvp.view_actions;

import com.example.moviesappmvp.models.Movie;

import java.util.List;

public interface ViewActionsMoviesList {
    void showProgress();

    void hideProgress();

    void setDataToViews(List<Movie> movieArrayList);

    void onResponseFailure(Throwable throwable);
}
