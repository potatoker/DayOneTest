package com.raymond.retrofittest.datatype;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by raymond on 16/3/12.
 */
public class Moment {
    private String id;
    private String photoURL;
    private Date date;
    private String desc;



    private String location;

    public Moment(String id, String photoURL, Date date, String desc) {
        this.id = id;
        this.photoURL = photoURL;
        this.date = date;
        this.desc = desc;
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

    public void setDate(Date date) {
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

    public Date getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }
}
