package com.example.moviesappmvp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.moviesappmvp.adapters.TabAdapter;
import com.example.moviesappmvp.tabs.NowPlayingMoviesFragment;
import com.example.moviesappmvp.tabs.PopularMoviesFragment;
import com.google.android.material.tabs.TabLayout;

public class MoviesListActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PopularMoviesFragment popularMoviesFragment = new PopularMoviesFragment();
        NowPlayingMoviesFragment nowPlayingMoviesFragment = new NowPlayingMoviesFragment();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(popularMoviesFragment, "Popular");
        adapter.addFragment(nowPlayingMoviesFragment, "Now Playing");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
