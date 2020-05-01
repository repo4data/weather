package com.example.weather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("parent_city")
    @Expose
    private String parentCity;
    @SerializedName("admin_area")
    @Expose
    private String adminArea;
    @SerializedName("cnty")
    @Expose
    private String cnty;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("tz")
    @Expose
    private String tz;
    @SerializedName("type")
    @Expose
    private String type;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParentCity() {
        return parentCity;
    }

    public void setParentCity(String parentCity) {
        this.parentCity = parentCity;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Basic.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cid");
        sb.append('=');
        sb.append(((this.cid == null)?"<null>":this.cid));
        sb.append(',');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("parentCity");
        sb.append('=');
        sb.append(((this.parentCity == null)?"<null>":this.parentCity));
        sb.append(',');
        sb.append("adminArea");
        sb.append('=');
        sb.append(((this.adminArea == null)?"<null>":this.adminArea));
        sb.append(',');
        sb.append("cnty");
        sb.append('=');
        sb.append(((this.cnty == null)?"<null>":this.cnty));
        sb.append(',');
        sb.append("lat");
        sb.append('=');
        sb.append(((this.lat == null)?"<null>":this.lat));
        sb.append(',');
        sb.append("lon");
        sb.append('=');
        sb.append(((this.lon == null)?"<null>":this.lon));
        sb.append(',');
        sb.append("tz");
        sb.append('=');
        sb.append(((this.tz == null)?"<null>":this.tz));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
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
        result = ((result* 31)+((this.tz == null)? 0 :this.tz.hashCode()));
        result = ((result* 31)+((this.parentCity == null)? 0 :this.parentCity.hashCode()));
        result = ((result* 31)+((this.adminArea == null)? 0 :this.adminArea.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.lon == null)? 0 :this.lon.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.cnty == null)? 0 :this.cnty.hashCode()));
        result = ((result* 31)+((this.lat == null)? 0 :this.lat.hashCode()));
        result = ((result* 31)+((this.cid == null)? 0 :this.cid.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Basic) == false) {
            return false;
        }
        Basic rhs = ((Basic) other);
        return ((((((((((this.tz == rhs.tz)||((this.tz!= null)&&this.tz.equals(rhs.tz)))&&((this.parentCity == rhs.parentCity)||((this.parentCity!= null)&&this.parentCity.equals(rhs.parentCity))))&&((this.adminArea == rhs.adminArea)||((this.adminArea!= null)&&this.adminArea.equals(rhs.adminArea))))&&((this.location == rhs.location)||((this.location!= null)&&this.location.equals(rhs.location))))&&((this.lon == rhs.lon)||((this.lon!= null)&&this.lon.equals(rhs.lon))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.cnty == rhs.cnty)||((this.cnty!= null)&&this.cnty.equals(rhs.cnty))))&&((this.lat == rhs.lat)||((this.lat!= null)&&this.lat.equals(rhs.lat))))&&((this.cid == rhs.cid)||((this.cid!= null)&&this.cid.equals(rhs.cid))));
    }

}
