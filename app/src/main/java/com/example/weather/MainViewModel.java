package com.example.weather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;

import com.example.weather.data.AppRepository;
import com.example.weather.data.DailyForecast;
import com.example.weather.util.DateUtils;
import com.example.weather.util.TemperatureUtil;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainViewModel extends AndroidViewModel {
    private final String KEY_NOTIFICATION = "NOTIFICATION";
    private final String KEY_TEMPERATURE_UNIT = "TEMPERATURE_UNIT";
    private final String KEY_LOCATION = "LOCATION";
    private final String KEY_LOCAL_CODE = "LOCAL_CODE";
    private SavedStateHandle mStateHandle;
    private SharedPreferences mPreferences;
    private AppRepository mRepository;


    private LiveData<List<DailyForecast>> mRawForecasts;

    private MediatorLiveData<List<DailyForecast>> mMediatorLiveData;

    private LiveData<List<DailyForecast>> mForecastsTransUnit;

    private LiveData<List<DailyForecast>> mForecastList;

    public LiveData<List<DailyForecast>> getForecastList() {
        return mForecastList;
    }

    private LiveData<DailyForecast> mToday;
    private LiveData<DailyForecast> mSelected;

    public LiveData<DailyForecast> getSelected() {
        return mSelected;
    }

    private MutableLiveData<Integer> mIndex;

    public MutableLiveData<Integer> getIndex() {
        return mIndex;
    }

    public LiveData<DailyForecast> getToday() {
        return mToday;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MainViewModel(@NonNull Application application, SavedStateHandle stateHandle) {
        super(application);

        mStateHandle = stateHandle;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext());
        mRepository = new AppRepository(getApplication().getApplicationContext());
        if (!stateHandle.contains(KEY_NOTIFICATION)) load();

        mRawForecasts = Transformations.switchMap(getLocalCode(),
                localCode -> mRepository.getRawForecasts(localCode));

        mMediatorLiveData = new MediatorLiveData<>();
        mMediatorLiveData.setValue(new ArrayList<>());
        mMediatorLiveData.addSource(mRawForecasts, forecasts -> mMediatorLiveData.setValue(forecasts));
        mMediatorLiveData.addSource(getUnitState(), aBoolean -> mMediatorLiveData.setValue(mMediatorLiveData.getValue()));

        mForecastsTransUnit = Transformations.map(mMediatorLiveData,
                forecasts -> forecasts.stream().parallel().map(forecast -> {
                    DailyForecast forecastCopy = new DailyForecast(forecast);
                    if (!getUnitState().getValue()) {
                        String max = forecast.getTmpMax();
                        String min = forecast.getTmpMin();
                        forecastCopy.setTmpMax(String.valueOf((int)TemperatureUtil.centigrade2Fahrenheit(Double.valueOf(max),0)));
                        forecastCopy.setTmpMax(String.valueOf((int)TemperatureUtil.centigrade2Fahrenheit(Double.valueOf(min),0)));
                    }
                    return forecastCopy;
                }).collect(Collectors.toList()));

        mForecastList = Transformations.map(mForecastsTransUnit,
                forecasts ->  forecasts.stream().parallel().map(forecast -> {
                    DailyForecast forecastCopy = new DailyForecast(forecast);
                    String date = forecast.getDate();
                    DateTime dateTime = new DateTime(date);
                    int diff = Days.daysBetween(DateTime.now().withTimeAtStartOfDay(), dateTime).getDays();
                    if (diff < 3) {
                        forecastCopy.setDate(DateUtils.transToAlias(diff));
                    }else {
                        forecastCopy.setDate(dateTime.dayOfWeek().getAsText(Locale.CHINA));
                    }
                    return forecastCopy;
                }).collect(Collectors.toList())
        );

        mToday = get(0);
        mIndex = new MutableLiveData<>();
        mIndex.setValue(0);
        mSelected = Transformations.switchMap(mIndex, this::get);
    }

    private LiveData<DailyForecast> get(int index) {
        return Transformations.map(mForecastsTransUnit, forecasts -> {
            if (forecasts.size()==0) {
                return null;
            }
            DailyForecast forecast = new DailyForecast(forecasts.get(index));
            DateTime dateTime = new DateTime(forecast.getDate());
            int diff = Days.daysBetween(DateTime.now().withTimeAtStartOfDay(), dateTime).getDays();
            String alias;
            if (diff < 3) {
                alias = (DateUtils.transToAlias(diff));
            }else {
                alias = (dateTime.dayOfWeek().getAsText(Locale.CHINA));
            }
            String date = dateTime.toString(DateUtils.dateFormat);
            forecast.setDate(String.format("%s, %s", alias, date));
            return forecast;
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        save();
    }

    private void load() {
        boolean notification = mPreferences.getBoolean(KEY_NOTIFICATION, false);
        mStateHandle.set(KEY_NOTIFICATION, notification);
        boolean usingCentigrade = mPreferences.getBoolean(KEY_TEMPERATURE_UNIT, true);
        mStateHandle.set(KEY_TEMPERATURE_UNIT, usingCentigrade);
        String location = mPreferences.getString(KEY_LOCATION,
                getApplication().getString(R.string.default_location));
        mStateHandle.set(KEY_LOCATION, location);
        String code = mPreferences.getString(KEY_LOCAL_CODE,
                getApplication().getString(R.string.default_localcode));
        mStateHandle.set(KEY_LOCAL_CODE, code);
    }

    public void save() {
        mPreferences.edit()
                .putBoolean(KEY_NOTIFICATION, getNotificationState().getValue())
                .putBoolean(KEY_TEMPERATURE_UNIT, getUnitState().getValue())
                .putString(KEY_LOCATION, getLocation().getValue())
                .putString(KEY_LOCAL_CODE, getLocalCode().getValue())
                .apply();
    }

    public MutableLiveData<Boolean> getNotificationState() {
        return mStateHandle.getLiveData(KEY_NOTIFICATION);
    }

    public MutableLiveData<Boolean> getUnitState() {
        return mStateHandle.getLiveData(KEY_TEMPERATURE_UNIT);
    }

    public MutableLiveData<String> getLocation() {
        return mStateHandle.getLiveData(KEY_LOCATION);
    }

    public MutableLiveData<String> getLocalCode() {
        return mStateHandle.getLiveData(KEY_LOCAL_CODE);
    }


}
