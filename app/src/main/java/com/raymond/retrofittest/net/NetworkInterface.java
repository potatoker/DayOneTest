package com.raymond.retrofittest.net;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.datatype.Comment;
import com.raymond.retrofittest.datatype.ExploreDay;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.datatype.User;
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
    public static final String KEY_USER = "user";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_EMAIL ="email";
    public static final String KEY_PWD = "pwd";
    public static final String KEY_EXPLORE_DAY = "explore_day";
    public static final String KEY_COMMENT = "comment";




    //服务器接口URL

    public static final String HOST_URL = "http://119.29.138.234:5000";

    public static final String BASE_API_URL = HOST_URL + "/one_day/controllers";
    public static final String API_REQUEST_DAYS = BASE_API_URL + "/request_days.php";
    public static final String API_POST_MOMENT_TEST = HOST_URL + "/moments";
    public static final String API_POST_MOMENT = HOST_URL + "/post_moment";
    public static final String API_POST_GET_MOMENTS = HOST_URL + "/all_moments";
    public static final String API_GET_TIMESTAMPS = HOST_URL + "all_moments";
    public static final String API_GET_EXPLORE_DAYS  = HOST_URL + "/explore_days";
    public static final String API_POST_COMMENT = HOST_URL + "/comment";
    public static final String API_SIGN_UP = HOST_URL + "/sign_up";
    public static final String API_LOGIN = HOST_URL + "/login";
    public static final String API_POST_EXPLORE_DAY = HOST_URL + "/post_explore2";


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


    public static void GetExploreDays(String uid, ResponseListener listener){
        Map<String, String> params = new HashMap<>();
        params.put(UID, uid);
        Request request = new PostCustomRequest(API_GET_EXPLORE_DAYS,
                params, new TypeToken<List<ExploreDay>>(){}.getType(),
                listener);

        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public static void PostExploreDay(ExploreDay exploreDay, ResponseListener listener){
        Map<String, String> params = new HashMap<>();
        Gson gson = new Gson();
        params.put(KEY_EXPLORE_DAY, gson.toJson(exploreDay));
        Request request = new PostCustomRequest(API_POST_EXPLORE_DAY,
                params, new TypeToken<String>(){}.getType(),
                listener);

        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    public static void PostComment(Comment comment, ResponseListener listener){
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put(KEY_COMMENT, gson.toJson(comment));
        Request request = new PostCustomRequest(API_POST_COMMENT,
                params, new TypeToken<Integer>(){}.getType(),
                listener);
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public static void SignUp(User user, String imgString, ResponseListener listener){
        Gson gson = new Gson();
        Map<String, String> params = new HashMap<>();
        params.put(KEY_USER, gson.toJson(user));
        params.put(KEY_PROFILE, imgString);

        Request request = new PostCustomRequest(API_SIGN_UP,
                params, new TypeToken<User>(){}.getType(),
                listener);
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public static void Login(String email, String pwd, ResponseListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_EMAIL, email);
        params.put(KEY_PWD, pwd);

        Request request = new PostCustomRequest(API_LOGIN,
                params, new TypeToken<User>(){}.getType(),
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
