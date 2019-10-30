package com.example.moviesappmvp.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesappmvp.MovieDetailsActivity;
import com.example.moviesappmvp.adapters.MoviesAdapter;
import com.example.moviesappmvp.R;
import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.presenters.MovieListPresenter;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingMoviesFragment extends ParentFragment {
    private static final String TAG = "MovieListActivity";
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private MovieListPresenter movieListPresenter;
    private RecyclerView rvMovieList;
    private List<Movie> moviesList;
    private MoviesAdapter moviesAdapter;
    private ProgressBar pbLoading;
    private TextView tvEmptyView;
    private int pageNo = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private GridLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_list_layout, container, false);

        buildUI(view);
        movieListPresenter = new MovieListPresenter(this);
        movieListPresenter.getNowPlayingMovies();

        return view;
    }


    private void buildUI(View view) {
        rvMovieList = view.findViewById(R.id.rv_movie_list);
        pbLoading = view.findViewById(R.id.pb_loading);

        moviesList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, moviesList);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMovieList.setLayoutManager(mLayoutManager);
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(moviesAdapter);


        tvEmptyView = view.findViewById(R.id.tv_empty_view);

        rvMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvMovieList.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    movieListPresenter.getMoreNowPlayingMovies(pageNo);
                    loading = true;
                }
            }
        });
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(List<Movie> movieArrayList) {
        moviesList.addAll(movieArrayList);
        moviesAdapter.notifyDataSetChanged();

        pageNo++;
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        Toast.makeText(getContext(), "Communication Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieListPresenter.onDestroy();
    }

    public void showEmptyView() {

        rvMovieList.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);

    }

    public void hideEmptyView() {
        rvMovieList.setVisibility(View.VISIBLE);
        tvEmptyView.setVisibility(View.GONE);
    }

    public void onMovieItemClick(int position) {
        if (position == -1) {
            return;
        }
        Intent detailIntent = new Intent(getContext(), MovieDetailsActivity.class);
        detailIntent.putExtra("movie_id", moviesList.get(position).getId());
        startActivity(detailIntent);
    }
}
