package com.raymond.retrofittest.tools;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.raymond.retrofittest.MyApplication;

/**
 * Created by raymond on 16/3/13.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapCache());
    }

    //使用内部内把实例变成
    private static class SingletonHolder{
        private static VolleySingleton instance = new VolleySingleton();
    }

    public static VolleySingleton getInstance(){
//        if(mInstance == null){
//            mInstance = new VolleySingleton();
//        }
//        return mInstance;

        return SingletonHolder.instance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

}
