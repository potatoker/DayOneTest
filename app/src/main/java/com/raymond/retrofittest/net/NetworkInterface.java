package com.raymond.retrofittest.net;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.tools.VolleySingleton;
import com.raymond.retrofittest.utils.Utils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raymond on 16/4/16.
 */
public class NetworkInterface {


    //网络接口相关常量
    public static final String USER_ID = "user_id";
    public static final String MOMENT = "moment";
    public static final String DAY_ID = "day_id";
    public static final String IMAGE_STRING = "image_string";
    public static final String UID = "uid";



    //服务器接口URL

    public static final String BASE_API_URL = "http://192.168.18.14:8088/one_day/controllers";
    public static final String API_REQUEST_DAYS = BASE_API_URL + "request_days.php";
    public static final String API_POST_MOMENT_TEST = "http://192.168.18.14:5000/moments";
    public static final String API_POST_MOMENT = "http://192.168.18.14:5000/post_moment";
    public static final String API_POST_GET_MOMENTS = "http://192.168.18.14:5000/all_moments";
    public static final String API_GET_TIMESTAMPS ="http://192.168.18.14:5000/all_moments";

    //返回值
    public static final String POST_SUCCESS = "ok";

    public static void PostRequestOneDay(String userId, String dayId, ResponseListener listener){
        Map<String, String> params = new HashMap<>();
        params.put(EnvVariable.USER_ID, userId);
        params.put(EnvVariable.DAY_ID, dayId);

        //这里的问题是TypeToken一般说是存在泛型类的时候用的，难道不是直接OneDay.class就可以吗，
        //毕竟Oneday这个类没有用到泛型，看到youjoin的作者全部用type形式，不知道合不合理
        //详见：http://www.jianshu.com/p/e740196225a4
        Request request = new PostCustomRequest(EnvVariable.API_REQUEST_ONE_DAY,
                params, new TypeToken<OneDay>(){}.getType(),
                listener);
        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    //其实想想干脆即使请求的不是一个列表，而是一个对象，干脆也包装成只有一个对象的列表，感觉方便点
    public static void PostRequestDays(String userId, ResponseListener listener){
        Map<String, String> params = new HashMap<>();
        params.put(USER_ID, userId);
        Request request = new PostCustomRequest(API_REQUEST_DAYS,
                params, new TypeToken<List<OneDay>>(){}.getType(),
                listener);
        VolleySingleton.getInstance().addToRequestQueue(request);

    }

    public static void PostMoment(Moment moment, String dayId, ResponseListener listener){
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put(MOMENT, gson.toJson(moment));
        params.put(DAY_ID, dayId);
        Request request = new PostCustomRequest(API_POST_MOMENT,
                params, String.class, listener);

        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public static void PostMoment2(Moment moment, String dayId, ResponseListener listener){
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put(MOMENT, gson.toJson(moment));
        params.put(DAY_ID, dayId);
        Request request = new PostCustomRequest(API_POST_MOMENT_TEST,
                params, new TypeToken<List<Moment>>(){}.getType(),
                listener);

        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    public static void GetMoments(String uid, ResponseListener listener){
        Gson gson = new Gson();
        Map<String,String> params = new HashMap<>();
        params.put(UID, uid);
        Request request = new PostCustomRequest(API_POST_GET_MOMENTS,
                params, new TypeToken<List<Moment>>(){}.getType(),
                listener);

        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public static void GetTimeStamps(String uid, ResponseListener listener){
        Map<String, String> params = new HashMap<>();
        params.put(UID, uid);
        Request request = new PostCustomRequest(API_GET_TIMESTAMPS,
                params, new TypeToken<List<String>>(){}.getType(),
                listener);

        VolleySingleton.getInstance().addToRequestQueue(request);
    }



//    public static void PostMomentWithImg(Moment moment, String dayId, String img,ResponseListener listener){
//        Gson gson = new Gson();
//        Map<String, String> params = new HashMap<>();
//        params.put(IMAGE_STRING, img);
//        params.put(DAY_ID, dayId);
//        Request request = new PostCustomRequest(API_POST_MOMENT_TEST,
//                params, new TypeToken<List<Moment>>(){}.getType(),
//                listener);
//        VolleySingleton.getInstance().addToRequestQueue(request);
//    }




}
