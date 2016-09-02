package com.raymond.retrofittest.datatype;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by raymond on 16/3/12.
 */
public class Moment implements Parcelable{


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

    private String remote_url;

    public String getRemote_url() {
        return remote_url;
    }

    public void setRemote_url(String remote_url) {
        this.remote_url = remote_url;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(photoURL);
        dest.writeString(date);
        dest.writeString(desc);
        dest.writeString(dayId);
        dest.writeString(location);
        dest.writeString(uid);
        dest.writeString(remote_url);
        dest.writeString(imgString);

    }

    public Moment(Parcel in){
        id = in.readString();
        photoURL = in.readString();
        date = in.readString();
        desc = in.readString();
        dayId = in.readString();
        location = in.readString();
        uid = in.readString();
        remote_url = in.readString();
        imgString = in.readString();
    }

    public static final Parcelable.Creator<Moment> CREATOR = new Parcelable.Creator<Moment>() {
        public Moment createFromParcel(Parcel in) {
            return new Moment(in);
        }

        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
}
