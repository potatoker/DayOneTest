package com.raymond.retrofittest.datatype;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by raymond on 8/29/16.
 */
public class ExploreDay {


    private String userid;

    @SerializedName("user_name")
    private String userName;

    private String profile_url;
    private ArrayList<Moment> moments = null;
    private String daytitle ="";
    private ArrayList<String> tags = null;
    private String desc;

    @SerializedName("day_id")
    private String dayId = "";

    private String time;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int thumup;


    public ExploreDay(String userid, String userName, String profile_url, ArrayList<Moment> moments, String daytitle, ArrayList<String> tags, String desc, String dayId, String time, ArrayList<Comment> comments) {
        this.userid = userid;
        this.userName = userName;
        this.profile_url = profile_url;
        this.moments = moments;
        this.daytitle = daytitle;
        this.tags = tags;
        this.desc = desc;
        this.dayId = dayId;
        this.time = time;
        this.comments = comments;
    }


    public ExploreDay(OneDay oneDay){
        this.userid = oneDay.getUserId();
        this.userName = User.getInstance().getName();
        this.profile_url = User.getInstance().getAvatarurl();
        this.moments = oneDay.getMoments();
        this.daytitle = oneDay.getDayTitle();
        this.tags = oneDay.getTags();
        this.desc = oneDay.getDesc();
        this.dayId = oneDay.getDayId();
        this.time = oneDay.getTime();
        this.comments = new ArrayList<Comment>();
        this.thumup=0;
    }


    public int getThumup() {
        return thumup;
    }

    public void setThumup(int thumup) {
        this.thumup = thumup;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public ArrayList<Moment> getMoments() {
        return moments;
    }

    public void setMoments(ArrayList<Moment> moments) {
        this.moments = moments;
    }

    public String getDaytitle() {
        return daytitle;
    }

    public void setDaytitle(String daytitle) {
        this.daytitle = daytitle;
    }

    public ArrayList<String> getTags() {
        return tags;
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

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
