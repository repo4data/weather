package com.example.weather;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.weather.data.DailyForecast;
import com.example.weather.data.HeWeatherResponse;
import com.example.weather.data.remote.ForecastService;
import com.example.weather.data.remote.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;

public class PromptService extends IntentService {
    private static final String TAG = "PromptService";
    private static final String channelId = "PromptService.Channel";
    private static final String channelName = "PromptService";
    private static final String channelDescription = "PromptService: new forecast coming";

    private static final long POLL_INTERVAL = 1000;

    public PromptService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PromptService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PromptService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PromptService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 这里是服务启动后的操作，具体来说是，获取网络数据并通知

        if (!isNetworkAvailableAndConnected()) {
            return;
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String locationCode = preferences.getString("LOCAL_CODE", "110101");

        ForecastService forecastService = RetrofitClient.getInstance().getForecastService();
        Call<HeWeatherResponse> forecastResponse = forecastService.getForecastResponse(locationCode);
        try {
            DailyForecast forecast = forecastResponse.execute().body().getHeWeather6().get(0).getDailyForecast().get(0);
            String s = String.format("预报：%s 最高气温%s° 最低气温%s°", forecast.getCondTxtD(), forecast.getTmpMax(), forecast.getTmpMin());
            // 发出通知
            Resources resources = getResources();
            Intent i = MainActivity.newIntent(this);
            PendingIntent pi = PendingIntent
                    .getActivity(this, 0, i, 0);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(true); //设置开启指示灯，如果设备有的话
                channel.setShowBadge(true); //设置是否显示角标
                channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);//设置是否应在锁定屏幕上显示此频道的通知
                channel.setDescription(channelDescription);//设置渠道描述
                channel.setBypassDnd(true);//设置是否绕过免打扰模式

                //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setTicker(resources.getString(R.string.new_ticker))
                    .setSmallIcon(R.drawable.level_list, Integer.valueOf(forecast.getCondCodeD()))
                    .setContentTitle(resources.getString(R.string.new_prompt_title))
                    .setContentText(s)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}
