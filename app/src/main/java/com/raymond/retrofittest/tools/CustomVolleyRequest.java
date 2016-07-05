package com.raymond.retrofittest.tools;

import android.content.Context;


import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by raymond on 16/3/13.
 */
public class CustomVolleyRequest {

    //这里用的是线程安全的懒汉式单例
    //这里我理解这个名字，是对应饿汉式，可以看到懒汉式是在代码中调用getInstance的时候才会生成，称之为懒
    //单例对象，缺点是需要同步语句，并且这里可以看到，每次getInstance都是在同步语句下，也就是说，如果多个
    //线程想要拿到instance，不仅在第一次Instance为Null的时候不可以同时访问getInstance(这是当然的，如果
    // 同时访问，线程间都会判定Instance初始为null,会同时创建instance,破坏了单例)，在以后任何时候想要获得这个
    //单例对象都需要同步，然而事实上，如果只是取得Instance对象完全可以同时取（线程异步的，有点绕）。
    //如果想要克服懒汉式的这一缺点需要用到双重检验锁参见：
    //http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/
    //即只把new instance的语句加上同步关键字，并声明instance为volatile即可:

    // A write operation to a volatile variable establishes
    // a happens-before relationship with all subsequent reads of that volatile variable.


    //hapens before 定义：
    //Let A and B represent operations performed by a multithreaded process. If A happens-before B,
    // then the memory effects of A effectively become visible to the thread performing B before B is performed.



    private static CustomVolleyRequest customVolleyRequest;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private CustomVolleyRequest(Context context){
        this.context = context;
        this.requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue, new BitmapCache());
    }


    //如果不加synchronized关键字，
    public static synchronized CustomVolleyRequest getInstance(Context context) {
        if(customVolleyRequest == null) {
            customVolleyRequest = new CustomVolleyRequest(context);
        }
        return customVolleyRequest;
    }


    public RequestQueue getRequestQueue(){
        if(requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return requestQueue;
    }


    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}

