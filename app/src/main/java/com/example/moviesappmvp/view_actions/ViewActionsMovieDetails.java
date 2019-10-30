package com.example.moviesappmvp.view_actions;

import com.example.moviesappmvp.models.Movie;

public interface ViewActionsMovieDetails {
    void showProgress();

    void hideProgress();

    void setDataToViews(Movie movie);

    void onResponseFailure(Throwable throwable);
}
