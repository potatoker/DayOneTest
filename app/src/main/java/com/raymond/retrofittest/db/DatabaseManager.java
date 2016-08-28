package com.raymond.retrofittest.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.raymond.retrofittest.MyApplication;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.datatype.TokenKeeper;
import com.raymond.retrofittest.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raymond on 6/14/16.
 */
public class DatabaseManager {
    private static final String TAG = "DatabaseManager";
    public static final String FAVADAY = "0000";

    private static TokenKeeper tk = new TokenKeeper(MyApplication.getAppContext());

    public static void addMoment(Moment moment){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());
        int newId = tk.getLastMomentId()+1;

        Moment moment1 = getMomentById(moment.getId());

        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.MOMENT_ID, newId);
        contentValues.put(DatabaseHelper.MOMENT_LOCATION, moment.getLocation());
        contentValues.put(DatabaseHelper.MOMENT_DESC, moment.getDesc());
        contentValues.put(DatabaseHelper.MOMENT_TIME,moment.getDate());
        contentValues.put(DatabaseHelper.MOMENT_URL, moment.getPhotoURL());
        contentValues.put(DatabaseHelper.DAY_ID, moment.getDayId());
        contentValues.put(DatabaseHelper.MOMENT_FLAG, moment.getFavaFlag());
        contentValues.put(DatabaseHelper.USER_ID, moment.getUid());
        contentValues.put(DatabaseHelper.MOMENT_SYNC, moment.getMoment_snyc());

        if(moment1 == null){
            contentValues.put(DatabaseHelper.MOMENT_ID, newId);
            db.insert(DatabaseHelper.TABLE_MOMENTS, null, contentValues);

            Log.d(TAG,"insert moment" + (newId));
            tk.increLastMomentId();
        }else{
            db.update(DatabaseHelper.TABLE_MOMENTS,contentValues,
                    DatabaseHelper.MOMENT_ID + " = ?", new String[]{moment1.getId()});

            Log.d(TAG, "update moment");
        }

    }



    public static void addDay(OneDay day){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String id = day.getDayId();
        int newId = tk.getLastDayId()+1;

//        if(day.getDayId().equals("")){
//            id = Integer.toString(newId);
//        }

//        OneDay day1 = getOneDayById(day.getDayId());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DAY_ID, day.getDayId());

//        Log.d(TAG, "dayId:" + newId);

        contentValues.put(DatabaseHelper.DAY_TIME, day.getTime());
        contentValues.put(DatabaseHelper.DAY_SYNC, day.getDay_sync());
//        Log.d(TAG, "dadd:" + day.getTime());
//        Log.d(TAG, "desc" + day.getDesc());

        contentValues.put(DatabaseHelper.DAY_TITLE, day.getDayTitle());
        contentValues.put(DatabaseHelper.DAY_FLAG, day.getFlag());

//        Log.d(TAG, "add day flag:" + day.getFlag());
        contentValues.put(DatabaseHelper.DAY_USER, day.getUserId());
        contentValues.put(DatabaseHelper.DAY_DESC, day.getDesc());


        if(day.getDayId().equals("")){
            contentValues.put(DatabaseHelper.ACCESS, DatabaseHelper.ACCESS_PRIVET);
            contentValues.put(DatabaseHelper.DAY_ID, newId);
            db.insert(DatabaseHelper.TABLE_DAYS, null, contentValues);
            Log.d(TAG, "add day:"+newId);
            tk.increLastDayId();
        }else{

            Log.d(TAG, "get update sentence");
            Log.d(TAG,"access to set now: " + day.getAccess());
            contentValues.put(DatabaseHelper.ACCESS, day.getAccess());
            int a = db.update(DatabaseHelper.TABLE_DAYS, contentValues,
                    DatabaseHelper.DAY_ID + " = ?", new String[]{day.getDayId()});

            Log.d(TAG,"update is "+ a);
        }
    }

    public static void deletCurrentDay(){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String sql = "delete from "
                + DatabaseHelper.TABLE_DAYS
                + " where " + DatabaseHelper.DAY_FLAG
                + " = " + DatabaseHelper.FLAG_DAY_CURRENT;

        db.delete(DatabaseHelper.TABLE_DAYS, DatabaseHelper.DAY_FLAG + " = ?", new String[]{Integer.toString(DatabaseHelper.FLAG_DAY_CURRENT)});
    }


    public static OneDay getCurrentDay(){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());
        String sql = "select * from "
                + DatabaseHelper.TABLE_DAYS
                + " where " + DatabaseHelper.DAY_FLAG
                + " = " + DatabaseHelper.FLAG_DAY_CURRENT;

        Log.d(TAG, "sql is :" + sql);

        List<OneDay> days = parseDayCursor(db.rawQuery(sql, null));

        Log.d(TAG, "days size: " + days.size());

        if (days.size() == 1){

            OneDay day = days.get(0);

            if(day!= null){
                Log.d(TAG, "day not null");

            }

            Log.d(TAG,"time:" + day.getTime());

//            if(Utils.CastStringToDate(day.getTime()).compareTo(Utils.CastStringToDate(Utils.getCurrentDate()))<0){
//                day.setFlag(DatabaseHelper.FLAG_DAY_NO_COMMIT);
//                addDay(day);
//
//                Log.d(TAG, "add current day " + day.getDayId());
//                return null;
//            }
//
//            else if(Utils.CastStringToDate(day.getTime()).compareTo(Utils.CastStringToDate(Utils.getCurrentDate())) == 0){
//                Log.d(TAG, "find current day" + day.getDayId());
//                return day;
//
//            }else{
//                return null;
//            }

            return day;

        }else {

            Log.d(TAG, "current day not find");

            return null;

        }
    }


    public static OneDay getCurrentDayWithMoments(){
        OneDay day = getCurrentDay();

        if(day == null){
            return null;
        }
        Log.d(TAG, "get from dayId" + day.getDayId());
        day.setMoments(getMomentByDayId(day.getDayId()));

        return day;

    }



    public static ArrayList<Moment> getFavaMoments(String uid){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String sql = "select * from "
                + DatabaseHelper.TABLE_MOMENTS
                + " where " + DatabaseHelper.USER_ID
                + " = " + uid
                + " and " + DatabaseHelper.MOMENT_FLAG
                + " = " + DatabaseHelper.FLAG_MOMENT_FAVA;

        String sql2 = "select * from "
                + DatabaseHelper.TABLE_MOMENTS
                + " where " + DatabaseHelper.MOMENT_FLAG
                + " = " + DatabaseHelper.FLAG_MOMENT_FAVA;

        ArrayList<Moment> moments = parseMomentCursor(db.rawQuery(sql2, null));
        return moments;
    }


    public static ArrayList<Moment> getMomentByDayId(String dayId){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String sql = "select * from "
                + DatabaseHelper.TABLE_MOMENTS
                + " where " + DatabaseHelper.DAY_ID
                + " = " + dayId;

        ArrayList<Moment> moments = parseMomentCursor(db.rawQuery(sql, null));

        if(moments.size() != 0){
//            Log.d(TAG, "get moment by dayId"+ dayId);
            return moments;
        }
        else return null;
    }

    public static Moment getMomentById(String momentId){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String sql = "select * from "
                + DatabaseHelper.TABLE_MOMENTS
                + " where " + DatabaseHelper.MOMENT_ID
                + " = " + momentId;

        List<Moment> moments = parseMomentCursor(db.rawQuery(sql, null));

        if(moments.size() == 1){
            return moments.get(0);
        }
        else return null;
    }

    public static OneDay getOneDayById(String dayId){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String sql = "select * from "
                + DatabaseHelper.TABLE_DAYS
                + " where " + DatabaseHelper.DAY_ID
                + " = " + dayId;

        List<OneDay> days = parseDayCursor(db.rawQuery(sql, null));
        if(days.size() == 1){
            return days.get(0);
        }
        else return null;
    }

    public static void deleteDay(OneDay day){
        ArrayList<Moment> moments = day.getMoments();

        for(Moment moment: moments){
            deleteMoment(moment);
        }

        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        db.delete(DatabaseHelper.TABLE_DAYS, DatabaseHelper.DAY_ID + " = ?", new String[]{day.getDayId()});
    }

    public static void deleteMoment(Moment moment){
        deleteMoment(moment.getId());
        Utils.deletePhotoByURI(moment.getPhotoURL());
    }


    public static void deleteMoment(String momentId){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());

        String sql = "delete from "
                + DatabaseHelper.TABLE_MOMENTS
                + " where " + DatabaseHelper.MOMENT_ID
                + " = " + momentId;
        db.delete(DatabaseHelper.TABLE_MOMENTS, DatabaseHelper.MOMENT_ID + " = ?", new String[]{momentId});
    }

    public static List<OneDay> getAllDays(){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());
        String sql = "select * from "
                + DatabaseHelper.TABLE_DAYS
                + " where " + DatabaseHelper.DAY_FLAG
                + " = " + DatabaseHelper.FLAG_DAY__COMMIT;

        List<OneDay> days = parseDayCursor(db.rawQuery(sql, null));

        for(OneDay day : days){
            day.setMoments(getMomentByDayId(day.getDayId()));
        }

        return days;
    }


    public static List<OneDay> getOpenDays(){
        SQLiteDatabase db = DatabaseHelper.getInstance(MyApplication.getAppContext());
        String sql = "select * from "
                + DatabaseHelper.TABLE_DAYS
                + " where "+ DatabaseHelper.ACCESS
                + " = " + DatabaseHelper.ACCESS_OPEN;

        List<OneDay> days = parseDayCursor(db.rawQuery(sql, null));

        for(OneDay day : days){
            day.setMoments(getMomentByDayId(day.getDayId()));
        }

        return days;



    }
    public static List<OneDay> getAllOpenDays(){
        return null;
    }

    private static ArrayList<Moment> parseMomentCursor(Cursor cursor){
        ArrayList<Moment> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Moment moment = new Moment();
                moment.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOMENT_TIME)));
                moment.setDesc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOMENT_DESC)));
                moment.setId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOMENT_ID)));
                moment.setLocation(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOMENT_LOCATION)));
                moment.setPhotoURL(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOMENT_URL)));
                moment.setDayId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAY_ID)));
                moment.setMoment_snyc(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MOMENT_SYNC)));
                moment.setUid(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_ID)));
                moment.setFavaFlag(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MOMENT_FLAG)));
                list.add(moment);
            }while (cursor.moveToNext());
        }

//        Log.d(TAG, "get moment size" + list.size());
        cursor.close();
        return list;
    }


    private static List<OneDay> parseDayCursor(Cursor cursor){
        List<OneDay> list = new ArrayList<>();

        int i = 0;

        if(cursor.moveToFirst()){
            do{
                OneDay day = new OneDay();

                day.setDesc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAY_DESC)));
                day.setTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAY_TIME)));
                day.setDayTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAY_TITLE)));
                day.setDayId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAY_ID)));
                day.setUserId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DAY_USER)));
                day.setFlag(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DAY_FLAG)));
                day.setAccess(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ACCESS)));
                day.setDay_sync(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DAY_SYNC)));
                list.add(day);
//                Log.d(TAG, "in parseDayCursor get: "+day.getDayId());
                i++;
            }while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

}
