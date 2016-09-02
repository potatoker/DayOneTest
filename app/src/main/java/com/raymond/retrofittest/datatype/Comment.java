package com.raymond.retrofittest.datatype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raymond on 8/29/16.
 */
public class Comment implements Parcelable{

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    private String comment;

    @SerializedName("day_id")
    private String dayId;

    @SerializedName("profile_img_url")
    private String profileImgUrl;

    public Comment(){

    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public Comment(String userId, String userName, String comment, String dayId, String profileImgUrl) {
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.dayId = dayId;
        this.profileImgUrl = profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(comment);
        dest.writeString(dayId);
        dest.writeString(profileImgUrl);
    }

    public Comment(Parcel in){
        userId = in.readString();
        userName = in.readString();
        comment = in.readString();
        dayId = in.readString();
        profileImgUrl = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
