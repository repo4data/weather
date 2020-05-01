package com.example.weather.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(indices = {@Index(value = {"location", "date"}, unique = true)}
        , primaryKeys = {"location", "date"})
public class DailyForecast {


    public DailyForecast() {
    }

    public DailyForecast(DailyForecast that) {
        this.location = that.location;
        this.condCodeD = that.condCodeD;
        this.condTxtD = that.condTxtD;
        this.date = that.date;
        this.hum = that.hum;
        this.pres = that.pres;
        this.tmpMax = that.tmpMax;
        this.tmpMin = that.tmpMin;
        this.windSpd = that.windSpd;
    }


    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    // 为了存储到数据库并区分不同位置的天气数据，这里相对于API添加了位置字段
    @NonNull
    private String location;
    @SerializedName("cond_code_d")
    @Expose
    private String condCodeD;

    @SerializedName("cond_txt_d")
    @Expose
    private String condTxtD;

    @NonNull
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("hum")
    @Expose
    private String hum;


    @SerializedName("pres")
    @Expose
    private String pres;

    @SerializedName("tmp_max")
    @Expose
    private String tmpMax;
    @SerializedName("tmp_min")
    @Expose
    private String tmpMin;

    @SerializedName("wind_spd")
    @Expose
    private String windSpd;

    public String getCondCodeD() {
        return condCodeD;
    }

    public void setCondCodeD(String condCodeD) {
        this.condCodeD = condCodeD;
    }


    public String getCondTxtD() {
        return condTxtD;
    }

    public void setCondTxtD(String condTxtD) {
        this.condTxtD = condTxtD;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }


    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }


    public String getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax) {
        this.tmpMax = tmpMax;
    }

    public String getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin) {
        this.tmpMin = tmpMin;
    }


    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }
}
