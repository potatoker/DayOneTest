package com.raymond.retrofittest.net;


import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.tools.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raymond on 16/4/16.
 */
public class NetworkInterface {


    public static void PostRequestOneDay(String userId, String dayId, ResponseListener listener){
        Map<String, String> params = new HashMap<>();
        params.put(EnvVariable.USER_ID, userId);
        params.put(EnvVariable.DAY_ID, dayId);

        //这里的问题是TypeToken一般说是存在泛型类的时候用的，难道不是直接OneDay.class就可以吗，
        //毕竟Onday这个类没有用到泛型，看到youjoin的作者全部用type形式，不知道合不合理
        //详见：http://www.jianshu.com/p/e740196225a4
        Request request = new PostCustomRequest(EnvVariable.API_REQUEST_ONE_DAY,
                params, new TypeToken<OneDay>(){}.getType(),
                listener);
        VolleySingleton.getInstance().addToRequestQueue(request);
    }
}
