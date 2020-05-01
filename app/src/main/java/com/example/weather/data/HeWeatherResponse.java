package com.example.weather.data;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeWeatherResponse {

    @SerializedName("HeWeather6")
    @Expose
    private List<HeWeather6> heWeather6 = new ArrayList<HeWeather6>();

    public List<HeWeather6> getHeWeather6() {
        return heWeather6;
    }

    public void setHeWeather6(List<HeWeather6> heWeather6) {
        this.heWeather6 = heWeather6;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HeWeatherResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("heWeather6");
        sb.append('=');
        sb.append(((this.heWeather6 == null)?"<null>":this.heWeather6));
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
        result = ((result* 31)+((this.heWeather6 == null)? 0 :this.heWeather6 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HeWeatherResponse) == false) {
            return false;
        }
        HeWeatherResponse rhs = ((HeWeatherResponse) other);
        return ((this.heWeather6 == rhs.heWeather6)||((this.heWeather6 != null)&&this.heWeather6 .equals(rhs.heWeather6)));
    }

}
