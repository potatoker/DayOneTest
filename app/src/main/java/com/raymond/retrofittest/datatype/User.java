package com.raymond.retrofittest.datatype;

/**
 * Created by raymond on 16/4/8.
 */

//singleton
public class User {
    private String email;
    private String uId;

    private String pwd;
    private String name;
    private String avatarURL;
    private String avatarURI;
    private String currentDayId;
    private String desc;
    private String motto;

    public String getAvatarURI() {
        return avatarURI;
    }

    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getCurrentDayId() {
        return currentDayId;
    }

    public void setCurrentDayId(String currentDayId) {
        this.currentDayId = currentDayId;
    }

    public User(String email, String uId, String pwd, String name, String avatarURL) {
        this.email = email;
        this.uId = uId;
        this.name = name;
        this.avatarURL = avatarURL;
        this.pwd = pwd;
    }

    public User() {
        this.email = "";
        this.uId = "";
        this.name = "";
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }


    //inner class to hold the instance
    private static class UserInfoHolder{
        private static User instance = new User();
    }

    public static User getInstance(){
        return UserInfoHolder.instance;
    }

}
