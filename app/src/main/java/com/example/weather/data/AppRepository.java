package com.example.weather.data;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.weather.data.local.AppDatabase;
import com.example.weather.data.local.DailyForecastDao;
import com.example.weather.data.remote.ForecastService;
import com.example.weather.data.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {
    private Context mContext;
    private DailyForecastDao mForecastDao;
    private ForecastService mForecastService;

    public AppRepository(Context context) {
        mContext = context;
        AppDatabase database = AppDatabase.getInstance(context);
        mForecastDao = database.getDailyForecastDao();
        RetrofitClient client = RetrofitClient.getInstance();
        mForecastService = client.getForecastService();
    }

    public LiveData<List<DailyForecast>> getRawForecasts(String locationCode) {
        refreshRawForecasts(locationCode);
        return mForecastDao.select(locationCode);
    }

    private void refreshRawForecasts(String locationCode) {
        mForecastService.getForecastResponse(locationCode).enqueue(new Callback<HeWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<HeWeatherResponse> call, @NonNull Response<HeWeatherResponse> response) {
                assert response.body() != null;
                List<DailyForecast> dailyForecasts = response.body().getHeWeather6().get(0).getDailyForecast();
                for (DailyForecast dailyForecast : dailyForecasts) {
                    dailyForecast.setLocation(locationCode);
                    mForecastDao.insert(dailyForecast);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HeWeatherResponse> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
