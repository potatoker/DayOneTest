package com.raymond.retrofittest;

import android.app.Application;
import android.content.Context;

/**
 * Created by raymond on 16/3/13.
 */
public class MyApplication extends Application {

    private  static MyApplication mInstance;
    private  static Context mAppContext;

    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }

    public static MyApplication getInstance() {
        return  mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

}