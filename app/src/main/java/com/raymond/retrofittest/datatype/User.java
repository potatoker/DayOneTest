package com.raymond.retrofittest.datatype;

/**
 * Created by raymond on 16/4/8.
 */

//singleton
public class User {
    private String email;
    private String uId;

    private String publicId;
    private String name;
    private String avatarURL;

    public User(String email, String uId, String publicId, String name, String avatarURL) {
        this.email = email;
        this.uId = uId;
        this.name = name;
        this.avatarURL = avatarURL;
        this.publicId = publicId;
    }

    public User() {
        this.email = "";
        this.uId = "";
        this.name = "";
        this.avatarURL = "";

    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
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
