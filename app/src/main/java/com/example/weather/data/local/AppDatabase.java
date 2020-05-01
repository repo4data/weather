package com.example.weather.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weather.data.DailyForecast;

@Database(entities = {DailyForecast.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DailyForecastDao getDailyForecastDao();
    // singleton
    private static AppDatabase sInstance;
    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, "app")
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
}
