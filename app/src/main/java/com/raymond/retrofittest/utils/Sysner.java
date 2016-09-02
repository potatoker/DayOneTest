package com.raymond.retrofittest.utils;

import android.content.Context;
import android.util.Log;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.datatype.ExploreDay;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 8/30/16.
 */
public class Sysner implements DataPresenter.PostExploreDay, DecodeImg.DecodeComp{
    private static final String TAG = "Sysner";

    List<OneDay> openDays = new ArrayList<>();
    List<ExploreDay> exploreDays = new ArrayList<>();
    private Context context;
    int moment_size = 0;
    int comp_moment_size = 0;


    public Sysner(Context context) {
        this.context = context;
    }

    public void startSysn(){
        loadOpenDays();
        exploreDays = getExploreFromOpenDays(openDays);
        getMomentsWithImgString(exploreDays);
    }



    public ArrayList<ExploreDay> getExploreFromOpenDays(List<OneDay> openDays){

        ArrayList<ExploreDay> exploreDays = new ArrayList<>();

        for(OneDay oneDay:openDays){
            ExploreDay exploreDay = new ExploreDay(oneDay);
            exploreDays.add(exploreDay);
        }
        return exploreDays;
    }


    public void getMomentsWithImgString(List<ExploreDay> openDays){
        for(ExploreDay openDay:openDays){
            for(Moment moment: openDay.getMoments()){
                DecodeImg decodeImg = new DecodeImg(moment, context);
                decodeImg.decodeComp = this;
                decodeImg.decode();
            }
        }
    }



    private void loadOpenDays(){

        openDays = DatabaseManager.getOpenDays();

        for(OneDay oneDay:openDays){
            moment_size+=oneDay.getMoments().size();
        }

        if(openDays == null){
            Log.d(TAG, "opendays null");
        }

        Log.d(TAG, "opendays nums:"+ openDays.size());

    }

    @Override
    public void onPostExploreDya(String dayId, Boolean ok) {
        Log.d(TAG, dayId+" post succeed");
    }

    @Override
    public void onDecodeFinish(Moment moment) {
        comp_moment_size++;
        if(comp_moment_size==moment_size){
            for(ExploreDay exploreDay : exploreDays){
                DataPresenter.PostExploreDay(exploreDay,this);
            }
        }
    }
}
