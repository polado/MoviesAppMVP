package com.example.moviesappmvp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.moviesappmvp.api.ApiClient;
import com.example.moviesappmvp.models.Movie;
import com.example.moviesappmvp.presenters.MovieDetailsPresenter;
import com.example.moviesappmvp.view_actions.ViewActionsMovieDetails;
import com.google.android.material.snackbar.Snackbar;

public class MovieDetailsActivity extends AppCompatActivity implements ViewActionsMovieDetails {
    private ImageView ivBackdrop;
    private ProgressBar pbLoadBackdrop;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieRatings;
    private TextView tvOverview;
    private TextView tvHomepageValue;
    private TextView tvTaglineValue;
    private TextView tvRuntimeValue;

    private String movieName;

    private MovieDetailsPresenter movieDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        buildUI();

        Intent mIntent = getIntent();
        int movieId = mIntent.getIntExtra("movie_id", 0);

        movieDetailsPresenter = new MovieDetailsPresenter(this);
        movieDetailsPresenter.requestMovieData(movieId);
    }

    private void buildUI() {

        ivBackdrop = findViewById(R.id.iv_backdrop);
        pbLoadBackdrop = findViewById(R.id.pb_load_backdrop);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_release_date);
        tvMovieRatings = findViewById(R.id.tv_movie_ratings);
        tvOverview = findViewById(R.id.tv_movie_overview);

        tvHomepageValue = findViewById(R.id.tv_homepage_value);
        tvTaglineValue = findViewById(R.id.tv_tagline_value);
        tvRuntimeValue = findViewById(R.id.tv_runtime_value);
    }

    @Override
    public void showProgress() {
        pbLoadBackdrop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoadBackdrop.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(Movie movie) {

        if (movie != null) {
            Log.d("MovieDetailsActivity", "Movie data received: " + movie.toString());
            movieName = movie.getTitle();
            tvMovieTitle.setText(movie.getTitle());
            tvMovieReleaseDate.setText(movie.getReleaseDate());
            tvMovieRatings.setText(String.valueOf(movie.getRating()));
            tvOverview.setText(movie.getOverview());

            Glide.with(this)
                    .load(ApiClient.BACKDROP_BASE_URL + movie.getBackdropPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                    .into(ivBackdrop);

            tvTaglineValue.setText(movie.getTagline() != null ? movie.getTagline() : "N/A");
            tvHomepageValue.setText(movie.getHomepage() != null ? movie.getHomepage() : "N/A");
            tvRuntimeValue.setText(movie.getRunTime() != null ? movie.getRunTime() : "N/A");
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(findViewById(R.id.main_content), "Error", Snackbar.LENGTH_LONG).show();
    }

}
