package com.example.moviesappmvp.presenters;

import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.models.MovieListModel;
import com.example.moviesappmvp.view_actions.ViewActionsMoviesList;

import java.util.List;

public class MovieListPresenter {
    private ViewActionsMoviesList movieListView;
    private MovieListModel movieListModel;

    public MovieListPresenter(ViewActionsMoviesList viewActions) {
        this.movieListView = viewActions;
        movieListModel = new MovieListModel();
    }

    public void onDestroy() {
        this.movieListView = null;
    }

    public void getMorePopularMovies(int pageNo) {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getPopularMovieList(new MovieListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                movieListView.setDataToViews(movieArrayList);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                movieListView.onResponseFailure(t);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }
        }, pageNo);
    }

    public void getPopularMovies() {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getPopularMovieList(new MovieListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                movieListView.setDataToViews(movieArrayList);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                movieListView.onResponseFailure(t);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }
        }, 1);
    }

    public void getMoreNowPlayingMovies(int pageNo) {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getNowPlayingMovieList(new MovieListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                movieListView.setDataToViews(movieArrayList);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                movieListView.onResponseFailure(t);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }
        }, pageNo);
    }

    public void getNowPlayingMovies() {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getNowPlayingMovieList(new MovieListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                movieListView.setDataToViews(movieArrayList);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                movieListView.onResponseFailure(t);
                if (movieListView != null) {
                    movieListView.hideProgress();
                }
            }
        }, 1);
    }
}
