package com.raymond.retrofittest;

import android.util.Log;

import com.android.volley.VolleyError;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.net.NetworkInterface;
import com.raymond.retrofittest.net.ResponseListener;

import java.util.List;

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


    public static void requestDays(String userId, final GetDaysInterface q){

        NetworkInterface.PostRequestDays(userId,
                new ResponseListener<List<OneDay>>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        q.onGetDays(null, false);
                    }

                    @Override
                    public void onResponse(List<OneDay> response) {
                        q.onGetDays(response, true);
                    }
                });

    }

    public static void postMoment2(Moment moment, String dayId, final PostMomentInterface2 q){
        NetworkInterface.PostMoment2(moment, dayId,
                new ResponseListener<List<Moment>>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        q.onPostMoment(null, false);
                    }

                    @Override
                    public void onResponse(List<Moment> response) {
                        q.onPostMoment(response, true);
                    }
                });

    }

    public static void postMoment(Moment moment, String dayId, final PostMomentInterface q){
        NetworkInterface.PostMoment(moment, dayId,
                new ResponseListener<String>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                        q.onPostMoment(null, false);
                    }

                    @Override
                    public void onResponse(String response) {
                        q.onPostMoment(response, true);
                    }
                });
    }




    //给调用的activity提供的回调函数的接口，让他们可以定义得到数据之后的动作
    public interface GetOneDayInterface{
        void onGetOneDay(OneDay day, Boolean ok);
    }

    public interface GetDaysInterface{
        void onGetDays(List<OneDay> days, Boolean ok);
    }

    public interface PostMomentInterface2 {
        void onPostMoment(List<Moment> moments, Boolean ok);
    }

    public interface PostMomentInterface{
        void onPostMoment(String flag, Boolean ok);
    }

}
