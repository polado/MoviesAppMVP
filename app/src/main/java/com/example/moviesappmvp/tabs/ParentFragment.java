package com.example.moviesappmvp.tabs;

import androidx.fragment.app.Fragment;

import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.view_actions.ViewActionsMoviesList;

import java.util.List;

public abstract class ParentFragment extends Fragment implements ViewActionsMoviesList {
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDataToViews(List<Movie> movieArrayList) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    public void onMovieItemClick(int position) {

    }
}
