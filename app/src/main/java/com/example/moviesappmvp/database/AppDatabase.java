package com.example.moviesappmvp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.moviesappmvp.models.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
