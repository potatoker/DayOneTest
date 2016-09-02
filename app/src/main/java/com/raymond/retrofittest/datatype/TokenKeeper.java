package com.raymond.retrofittest.datatype;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by raymond on 6/14/16.
 */
public class TokenKeeper {

    public static final String KEY_UID = "uid";
    //第三方登陆的token：
//    public static final String KEY_ACCESS_TOKEN = "access_token";
    //第三方登陆的有效期
//    public static final String KEY_EXPIRES_IN = "expires_in";
    public static final String IS_USER_LOGIN =  "IsUserLogin";
//    public static final String KEY_PASSWORD = "password";
    public static final String TOKEN_TYPE = "token_type";
    public static final String KEY_AVATER_LOCAL = "avatar_local";
    private static final String PREFERENCES_NAME = "user_token";
    private static final String KEY_NAME =  "name";
    private static final String KEY_LAST_DAY_ID = "last_day_id";
    private static final String KEY_LAST_MOMENT_ID = "last_moment_id";
    private static final String ALARM_OPEN="alarm_open";
    private static final String KEY_AVATAR_REMOTE = "avatar_remote";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PWD = "pwd";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public TokenKeeper(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFERENCES_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public HashMap<String, String> getToken(){
        HashMap<String, String> token = new HashMap<String, String>();
        token.put(KEY_UID, pref.getString(KEY_UID, null));
        token.put(KEY_AVATER_LOCAL, pref.getString(KEY_AVATER_LOCAL, null));
        token.put(TOKEN_TYPE, pref.getString(TOKEN_TYPE, null));
        token.put(KEY_NAME, pref.getString(KEY_NAME, null));
        return token;
    }

    public void getUser(){
        User user = User.getInstance();
        user.setAvatarurl(pref.getString(KEY_AVATAR_REMOTE, null));
        user.setUid(pref.getString(KEY_UID, null));
        user.setName(pref.getString(KEY_NAME, null));
        user.setEmail(pref.getString(KEY_EMAIL, null));
    }


    public void createUserLoginSession(String uid, String avatar_local, String type, String name, int dayId, int momentId){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_UID, uid);
        editor.putString(KEY_AVATER_LOCAL, avatar_local);
        editor.putString(KEY_NAME, name);
        editor.putString(TOKEN_TYPE, type);
        editor.putString(KEY_NAME, name);
        editor.putInt(KEY_LAST_DAY_ID, dayId);
        editor.putInt(KEY_LAST_MOMENT_ID, momentId);
        editor.commit();
    }

    public void setUser(User user){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_UID, user.getUid());
        editor.putString(KEY_AVATAR_REMOTE, user.getAvatarurl());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PWD, user.getPwd());
        editor.commit();
    }


    public void setAlarmOpen(Boolean bool){
        editor.putBoolean(ALARM_OPEN, bool);
        editor.commit();
    }

    public Boolean getAlarmOpen(){
        return pref.getBoolean(ALARM_OPEN, false);
    }



    public int getLastDayId(){
        return pref.getInt(KEY_LAST_DAY_ID, -1);
    }

    public int getLastMomentId(){
        return pref.getInt(KEY_LAST_MOMENT_ID, -1);
    }

    public void increLastMomentId(){
        editor.putInt(KEY_LAST_MOMENT_ID, getLastMomentId()+1);
        editor.commit();
    }

    public void increLastDayId(){
        editor.putInt(KEY_LAST_DAY_ID, getLastDayId()+1);
        editor.commit();
    }

    public void logoutUser(){
        editor.putBoolean(IS_USER_LOGIN, false);
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        //表示如果没有这个key域的话就返回flase
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
