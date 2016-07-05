package com.raymond.retrofittest.tools;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.raymond.retrofittest.MyApplication;

/**
 * Created by raymond on 16/3/13.
 */
public class VolleySingleton {

    private static final String TAG = "VolleySingleton";
    //关于单例的各类模式，参见：http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(){

        //可以发现在CustomVolleyRequest这另一个相同功能的单例类里面用的构造方法是new
        //需要传递两个参数，cache 和network，并且需要手动启动.
        //其实这里用的方法使用的是默认的设置，如果需要定制，应该用new
        if(MyApplication.getAppContext() == null){
            Log.d(TAG, "context null");
        }
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

        mImageLoader = new ImageLoader(this.mRequestQueue, new BitmapCache());
    }

    //使用内部内把实例变成
    private static class SingletonHolder{
        private static VolleySingleton instance = new VolleySingleton();
    }

    public static VolleySingleton getInstance(){
        return SingletonHolder.instance;
    }

    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req){
        int socketTimeout = 90000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

}
