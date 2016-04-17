package com.raymond.retrofittest.datatype;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by raymond on 16/4/16.
 */
public class OneDay {
    private String userId = "";
    private ArrayList<Moment> moments = null;
    private String dayTitle = "";
    private ArrayList<String> tags = null;

    public OneDay() {

    }

    public OneDay(String userId, ArrayList<Moment> moments, String dayTitle, ArrayList<String> tags) {
        this.userId = userId;
        this.moments = moments;
        this.dayTitle = dayTitle;
        this.tags = tags;
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
}
