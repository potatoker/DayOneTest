package com.raymond.retrofittest;

import android.util.Log;

import com.android.volley.VolleyError;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.net.NetworkInterface;
import com.raymond.retrofittest.net.ResponseListener;

/**
 * Created by raymond on 16/4/16.
 */
public class DataPresenter {

    private static final String TAG = "DataPresenter";

    public static void requestOneDay(String userId, String dayId, final GetOneDayInterface q){
        NetworkInterface.PostRequestOneDay(userId, dayId,
                new ResponseListener<OneDay>() {
                    @Override
                    public void onResponse(OneDay response) {
                        q.onGetOneDay(response, true);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        OneDay oneDay= new OneDay();
                        q.onGetOneDay(oneDay, false);
                    }
                });
    }

    //给调用的activity提供的回调函数的接口，让他们可以定义得到数据之后的动作
    public interface GetOneDayInterface{
        void onGetOneDay(OneDay day, Boolean ok);
    }
}
