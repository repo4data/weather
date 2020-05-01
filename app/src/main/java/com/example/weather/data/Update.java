package com.example.weather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Update {

    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("utc")
    @Expose
    private String utc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Update.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("loc");
        sb.append('=');
        sb.append(((this.loc == null)?"<null>":this.loc));
        sb.append(',');
        sb.append("utc");
        sb.append('=');
        sb.append(((this.utc == null)?"<null>":this.utc));
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
        result = ((result* 31)+((this.loc == null)? 0 :this.loc.hashCode()));
        result = ((result* 31)+((this.utc == null)? 0 :this.utc.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Update) == false) {
            return false;
        }
        Update rhs = ((Update) other);
        return (((this.loc == rhs.loc)||((this.loc!= null)&&this.loc.equals(rhs.loc)))&&((this.utc == rhs.utc)||((this.utc!= null)&&this.utc.equals(rhs.utc))));
    }

}
