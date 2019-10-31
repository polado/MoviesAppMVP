package com.example.moviesappmvp.presenters;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moviesappmvp.database.MovieDao;
import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.models.MovieListModel;
import com.example.moviesappmvp.view_actions.ViewActionsMoviesList;

import java.util.List;

public class MovieListPresenter {
    private ViewActionsMoviesList movieListView;
    private MovieListModel movieListModel;

    static public MutableLiveData<List<Movie>> isFavouriteLiveData = new MutableLiveData<>();
    private MovieDao movieDao;

    public MovieListPresenter(ViewActionsMoviesList viewActions, MovieDao movieDao) {
        this.movieListView = viewActions;
        movieListModel = new MovieListModel();
        this.movieDao = movieDao;
    }

    public void onDestroy() {
        this.movieListView = null;
    }

    public void getFavouriteMovies() {
        SetFavouritesTask t = new SetFavouritesTask();
        t.execute();
    }

    public void getMorePopularMovies(int pageNo) {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getPopularMovieList(new MovieListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                if (movieListView != null) {
                    movieListView.setDataToViews(movieArrayList);
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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Movie> list = movieDao
                        .getAll();
                Log.e("MovieListPresenter", "Movies in db" + list.toString());

                movieListModel.getPopularMovieList(new MovieListModel.OnFinishedListener() {
                    @Override
                    public void onFinished(final List<Movie> movieArrayList) {

                        for (Movie m : movieArrayList) {
                            for (Movie l : list) {
                                if (l.getId() == m.getId()) {
                                    Log.e("MovieListPresenter", "db contains" + m.toString());
                                    m.isFavourite = true;
                                }
                            }
                        }

                        if (movieListView != null) {
                            movieListView.setDataToViews(movieArrayList);
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
        });
    }

    public void getMoreNowPlayingMovies(int pageNo) {
        if (movieListView != null) {
            movieListView.showProgress();
        }
        movieListModel.getNowPlayingMovieList(new MovieListModel.OnFinishedListener() {
            @Override
            public void onFinished(List<Movie> movieArrayList) {
                if (movieListView != null) {
                    movieListView.setDataToViews(movieArrayList);
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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Movie> list = movieDao
                        .getAll();
                Log.e("MovieListPresenter", "Movies in db" + list.toString());

                movieListModel.getNowPlayingMovieList(new MovieListModel.OnFinishedListener() {
                    @Override
                    public void onFinished(final List<Movie> movieArrayList) {

                        for (Movie m : movieArrayList) {
                            for (Movie l : list) {
                                if (l.getId() == m.getId()) {
                                    Log.e("MovieListPresenter", "db contains" + m.toString());
                                    m.isFavourite = true;
                                }
                            }
                        }

                        if (movieListView != null) {
                            movieListView.setDataToViews(movieArrayList);
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
        });
    }

    public void refresh() {

    }

    public void getFavourites() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> list = movieDao
                        .getAll();
            }
        });
    }

    public void toggleFavourite(final Movie movie, final boolean isFavourite) {
        movie.isFavourite = isFavourite;
        Task t = new Task();
        t.execute(movie);
    }

    private class Task extends AsyncTask<Movie, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Movie... movies) {
            if (movies[0].isFavourite) movieDao
                    .insert(movies[0]);
            else movieDao
                    .delete(movies[0]);

            List<Movie> list = movieDao
                    .getAll();
            Log.e("MovieListPresenter", "toggle Movies in db " + list.size());
            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            isFavouriteLiveData.setValue(movies);
            if (movieListView != null)
                movieListView.setDataToViews(movies);
            super.onPostExecute(movies);
        }
    }

    private class SetFavouritesTask extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected void onPreExecute() {
            movieListView.showProgress();
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            List<Movie> list = movieDao
                    .getAll();
            Log.e("MovieListPresenter", "SetFavouritesTask Movies in db " + list.size());
            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieListView.hideProgress();
            isFavouriteLiveData.setValue(movies);
            if (movieListView != null)
                movieListView.setDataToViews(movies);
            super.onPostExecute(movies);
        }
    }
}
