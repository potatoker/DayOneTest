package com.raymond.retrofittest.datatype;

/**
 * Created by raymond on 16/4/8.
 */

//singleton
public class User {
    private String email;
    private String uid;
    private String pwd;
    private String name;
    private String avatarurl;
    private String avataruri;
    private String currentdayid;
    private String desc;
    private String motto;

    public String getAvataruri() {
        return avataruri;
    }

    public void setAvataruri(String avataruri) {
        this.avataruri = avataruri;
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

    public String getCurrentdayid() {
        return currentdayid;
    }

    public void setCurrentdayid(String currentdayid) {
        this.currentdayid = currentdayid;
    }

    public User(String email, String uid, String pwd, String name, String avatarurl) {
        this.email = email;
        this.uid = uid;
        this.name = name;
        this.avatarurl = avatarurl;
        this.pwd = pwd;
    }

    public User() {
        this.email = "";
        this.uid = "";
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }


    //inner class to hold the instance
    private static class UserInfoHolder{
        private static User instance = new User();
    }

    public static void setUserSingleton(User user){
        User.getInstance().setName(user.getName());
        User.getInstance().setUid(user.getUid());
        User.getInstance().setEmail(user.getEmail());
        User.getInstance().setDesc(user.getDesc());
        User.getInstance().setAvataruri(user.getAvataruri());
        User.getInstance().setAvatarurl(user.getAvatarurl());
        User.getInstance().setMotto(user.getMotto());
        User.getInstance().setPwd(user.getPwd());
        User.getInstance().setCurrentdayid(user.getCurrentdayid());
    }

    public static User getInstance(){
        return UserInfoHolder.instance;
    }

}
