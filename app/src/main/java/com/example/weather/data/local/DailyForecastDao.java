package com.example.weather.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.weather.data.DailyForecast;

import java.util.List;

@Dao
public interface DailyForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DailyForecast forecast);
    @Query("select * from dailyforecast where location=:location")
    LiveData<List<DailyForecast>> select(String location);
}
