package com.raymond.retrofittest;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.VolleyError;
import com.raymond.retrofittest.datatype.Comment;
import com.raymond.retrofittest.datatype.ExploreDay;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.net.NetworkInterface;
import com.raymond.retrofittest.net.ResponseListener;

import java.io.ByteArrayOutputStream;
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

    public static void postMomentWithImg(Moment moment, String dayId, String img, final PostMomentWithImgInterface q){

    }

    public static void GetMoments(String uid, final GetMoments q){
        NetworkInterface.GetMoments(uid, new ResponseListener<List<Moment>>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                q.onGetMoments(null, false);
            }

            @Override
            public void onResponse(List<Moment> response) {
                q.onGetMoments(response, true);
            }
        });
    }

    public static void GetStamps(String uid, final GetStamps q){
        NetworkInterface.GetTimeStamps(uid, new ResponseListener<List<String>>(){

            @Override
            public void onResponse(List<String> response) {
                q.onGetStamps(response,true);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                q.onGetStamps(null, false);
            }
        });
    }

    public static void GetExploreDays(String uid, final GetExploreDays q){
        NetworkInterface.GetExploreDays(uid, new ResponseListener<List<ExploreDay>>(){

            @Override
            public void onResponse(List<ExploreDay> response) {
                q.onGetExploreDays(response,true);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                q.onGetExploreDays(null, false);
            }
        });
    }

    public static void PostComment(Comment comment, final PostComment q){
        NetworkInterface.PostComment(comment, new ResponseListener<Integer>(){

            @Override
            public void onResponse(Integer response) {
                q.onPostComment(response,true);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                q.onPostComment(0, false);
            }
        });
    }

    public static void SignUp(User user, String imgString, final SignUp q){
        NetworkInterface.SignUp(user, imgString, new ResponseListener<User>(){

            @Override
            public void onResponse(User response) {
                q.onSignUp(response, true);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                q.onSignUp(null,false);
            }
        });
    }

    public static void Login(String email, String pwd, final Login q){
        NetworkInterface.Login(email, pwd, new ResponseListener<User>(){

            @Override
            public void onResponse(User response) {
                q.onLogin(response, true);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
                q.onLogin(null, false);
            }
        });
    }

    public static void PostExploreDay(ExploreDay exploreDay, final PostExploreDay q){
        NetworkInterface.PostExploreDay(exploreDay, new ResponseListener<String>(){

            @Override
            public void onResponse(String response) {
                q.onPostExploreDya(response, true);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
                q.onPostExploreDya(null, false);
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

    public interface PostMomentWithImgInterface{

    }

    public interface GetMoments{
        void onGetMoments(List<Moment> moments, Boolean ok);
    }

    public interface  GetStamps{
        void onGetStamps(List<String> dates, Boolean ok);
    }

    public interface GetExploreDays{
        void onGetExploreDays(List<ExploreDay> exploreDays, Boolean ok);
    }

    public interface PostComment{
        void onPostComment(int i, Boolean ok);
    }

    public interface Login{
        void onLogin(User user, Boolean ok);
    }

    public interface SignUp{
        void onSignUp(User user, Boolean ok);
    }

    public interface PostExploreDay{
        void onPostExploreDya(String dayId, Boolean ok);
    }

}
