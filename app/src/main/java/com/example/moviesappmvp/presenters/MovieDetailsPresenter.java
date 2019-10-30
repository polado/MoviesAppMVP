package com.example.moviesappmvp.presenters;

import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.models.MovieDetailsModel;
import com.example.moviesappmvp.view_actions.ViewActionsMovieDetails;

public class MovieDetailsPresenter {
    ViewActionsMovieDetails movieDetailsView;
    MovieDetailsModel movieDetailsModel;

    public MovieDetailsPresenter(ViewActionsMovieDetails movieDetailView) {
        this.movieDetailsView = movieDetailView;
        this.movieDetailsModel = new MovieDetailsModel();
    }

    public void onDestroy() {

        movieDetailsView = null;
    }

    public void requestMovieData(int movieID) {

        if (movieDetailsView != null) {
            movieDetailsView.showProgress();
        }
        movieDetailsModel.getMovieDetails(new MovieDetailsModel.OnFinishedListener() {
            @Override
            public void onFinished(Movie movie) {
                if (movieDetailsView != null) {
                    movieDetailsView.hideProgress();
                }
                movieDetailsView.setDataToViews(movie);
            }

            @Override
            public void onFailure(Throwable t) {
                if (movieDetailsView != null) {
                    movieDetailsView.hideProgress();
                }
                movieDetailsView.onResponseFailure(t);
            }
        }, movieID);
    }

    public void onFinished(Movie movie) {

        if (movieDetailsView != null) {
            movieDetailsView.hideProgress();
        }
        movieDetailsView.setDataToViews(movie);
    }

    public void onFailure(Throwable t) {
        if (movieDetailsView != null) {
            movieDetailsView.hideProgress();
        }
        movieDetailsView.onResponseFailure(t);
    }
}
