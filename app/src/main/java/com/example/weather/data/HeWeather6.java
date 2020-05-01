package com.example.weather.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeWeather6 {
    @SerializedName("basic")
    @Expose
    private Basic basic;
    @SerializedName("update")
    @Expose
    private Update update;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("daily_forecast")
    @Expose
    private List<DailyForecast> dailyForecast = new ArrayList<DailyForecast>();

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecast> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HeWeather6 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("basic");
        sb.append('=');
        sb.append(((this.basic == null)?"<null>":this.basic));
        sb.append(',');
        sb.append("update");
        sb.append('=');
        sb.append(((this.update == null)?"<null>":this.update));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("dailyForecast");
        sb.append('=');
        sb.append(((this.dailyForecast == null)?"<null>":this.dailyForecast));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.update == null)? 0 :this.update.hashCode()));
        result = ((result* 31)+((this.basic == null)? 0 :this.basic.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        result = ((result* 31)+((this.dailyForecast == null)? 0 :this.dailyForecast.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HeWeather6) == false) {
            return false;
        }
        HeWeather6 rhs = ((HeWeather6) other);
        return (((((this.update == rhs.update)||((this.update!= null)&&this.update.equals(rhs.update)))&&((this.basic == rhs.basic)||((this.basic!= null)&&this.basic.equals(rhs.basic))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))))&&((this.dailyForecast == rhs.dailyForecast)||((this.dailyForecast!= null)&&this.dailyForecast.equals(rhs.dailyForecast))));
    }

}
