package com.example.weather.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://free-api.heweather.net/s6/weather/";
    private static RetrofitClient sRetrofitClient;
    private ForecastService mForecastService;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mForecastService = retrofit.create(ForecastService.class);
    }
    // singleton
    public static synchronized RetrofitClient getInstance() {
        if (sRetrofitClient == null) {
            sRetrofitClient = new RetrofitClient();
        }
        return sRetrofitClient;
    }

    public ForecastService getForecastService() {
        return mForecastService;
    }
}
