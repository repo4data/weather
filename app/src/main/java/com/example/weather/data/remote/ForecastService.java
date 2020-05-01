package com.example.weather.data.remote;

import com.example.weather.data.HeWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {
   // localCode：位置的经纬度或者邮编
   @GET("forecast?key=58e95fe1ade943c995845f68c8b84033")
   Call<HeWeatherResponse> getForecastResponse(@Query("location")String localCode);
}

