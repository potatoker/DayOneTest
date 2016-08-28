package com.raymond.retrofittest.datatype;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by raymond on 16/3/12.
 */
public class Moment {


    public static final String IMG_PIXEL_NULL = "null";

    @SerializedName("moment_id")
    private String id;

    @SerializedName("photo_url")
    private String photoURL;

    private String date;

    private String desc;

    @SerializedName("day_id")
    private String dayId;

    private String location;
    private String uid;



    @SerializedName("img_string")
    private String imgString=IMG_PIXEL_NULL;


    private int moment_snyc=0;

    @SerializedName("fava_flag")
    private int favaFlag=0;


    public int getMoment_snyc() {
        return moment_snyc;
    }

    public void setMoment_snyc(int moment_snyc) {
        this.moment_snyc = moment_snyc;
    }

    public int getFavaFlag() {
        return favaFlag;
    }

    public void setFavaFlag(int favaFlag) {
        this.favaFlag = favaFlag;
    }

    public Moment(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Moment(String id, String photoURL, String date, String desc, String location, String dayId) {
        this.id = id;
        this.photoURL = photoURL;
        this.date = date;
        this.desc = desc;
        this.location = location;
        this.dayId = dayId;
    }

    public Moment(String photoURL, String date, String desc, String location,String dayId) {
        this.photoURL = photoURL;
        this.date = date;
        this.desc = desc;
        this.location = location;
        this.dayId = dayId;
    }

    public Moment(String photoURL, String date, String desc, String location) {
        this.photoURL = photoURL;
        this.date = date;
        this.desc = desc;
        this.location = location;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Moment fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Moment.class);
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }
}
