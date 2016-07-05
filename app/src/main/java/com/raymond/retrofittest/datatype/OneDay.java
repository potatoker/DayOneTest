package com.raymond.retrofittest.datatype;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by raymond on 16/4/16.
 */
public class OneDay {
    private String userId = "";
    private ArrayList<Moment> moments = null;
    private String dayTitle = "no";
    private ArrayList<String> tags = null;
    private String desc;
    private String dayId="";
    private String time;
    private int day_sync;
    private int flag;
    private int access;

    public int getDay_sync() {
        return day_sync;
    }

    public void setDay_sync(int day_sync) {
        this.day_sync = day_sync;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public OneDay() {

    }

    public OneDay(String userId, ArrayList<Moment> moments, String dayTitle, ArrayList<String> tags, String desc) {
        this.userId = userId;
        this.moments = moments;
        this.dayTitle = dayTitle;
        this.tags = tags;
        this.desc = desc;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Moment> getMoments() {
        return moments;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMoments(ArrayList<Moment> moments) {
        this.moments = moments;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
