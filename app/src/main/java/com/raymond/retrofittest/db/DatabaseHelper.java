package com.raymond.retrofittest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raymond on 5/30/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int FLAG_DAY_NO_COMMIT = 0;
    public static final int FLAG_DAY__COMMIT = 1;
    public static final int FLAG_DAY_CURRENT = 2;


    public static final int SYNC_OK = 1;
    public static final int SYNC_NOT = 0;

    public static final int ACCESS_OPEN = 1;
    public static final int ACCESS_PRIVET =0;

    private static SQLiteDatabase mDb;
    private static DatabaseHelper mHelper;

    public static final String DB_NAME = "dayone_db";
    public static final int DB_VERSION = 1;
    public static final String DROP_TABLE = "drop table if exists ";

    public static final String TABLE_DAYS  = "table_day";
    public static final String TABLE_MOMENTS = "table_moments";

    public static final String DAY_ID = "day_id";
    public static final String DAY_TIME = "day_time";
    public static final String DAY_USER = "day_user";
    public static final String DAY_FLAG = "day_flag";
    public static final String DAY_TITLE = "day_title";
    public static final String DAY_DESC = "day_desc";
    public static final String DAY_SYNC =  "day_sync";
    public static final String ACCESS = "access";



// 警告，如果添加项，一定要修改databaseManager里的映射关系
    private static final String CREATE_TABLE_DAYS = "create table if not exists "
            + TABLE_DAYS
            + "(" + DAY_ID + " integer primary key, "
            + DAY_TIME + " text not null, "
            + DAY_FLAG + " integer not null, "
            + DAY_TITLE + " text, "
            + DAY_USER + " text not null, "
            + DAY_SYNC + " integer not null, "
            + ACCESS + " integer not null, "
            + DAY_DESC + " blob) ";


    public static final String MOMENT_ID = "moment_id";
    public static final String MOMENT_TIME = "moment_time";
    public static final String MOMENT_NUMBER = "moment_number";
    public static final String MOMENT_DESC = "moment_desc";
    public static final String MOMENT_URL = "moment_url";
    public static final String MOMENT_URI = "moment_uri";
    public static final String MOMENT_LOCATION = "moment_location";
    public static final String MOMENT_FLAG = "moment_flag";
    public static final String USER_ID = "user_id";
    public static final String MOMENT_SYNC = "moment_sync";


    public static final int FLAG_MOMENT_FAVA = 1;


    private static final String CREATE_TABLE_MOMENTS = "create table if not exists "
            + TABLE_MOMENTS
            + "(" + MOMENT_ID + " integer primary key, "
            + MOMENT_TIME + " text not null, "
//            + MOMENT_NUMBER + " integer, "
            + MOMENT_DESC + " blob, "
            + MOMENT_URL + " text not null, "
            + MOMENT_LOCATION + " text, "
            + MOMENT_FLAG + " integer not null, "
            + USER_ID + " text not null, "
            + MOMENT_SYNC + " integer not null, "
            + DAY_ID + " text)";
//            + MOMENT_URI + " text not null)";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db;
        db = getWritableDatabase();
    }


    public static SQLiteDatabase getInstance(Context context){
        if(mDb == null){
            mDb = getHelper(context).getWritableDatabase();
        }
        return mDb;
    }


    public static DatabaseHelper getHelper(Context context){
        if(mHelper == null){
            mHelper = new DatabaseHelper(context);
        }
        return mHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DROP_TABLE + TABLE_DAYS);
//        db.execSQL(DROP_TABLE + TABLE_MOMENTS);
//        db.delete(TABLE_DAYS, null, null);
//        db.delete(TABLE_MOMENTS, null, null);
        db.execSQL(CREATE_TABLE_DAYS);
        db.execSQL(CREATE_TABLE_MOMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE + TABLE_DAYS);
        db.execSQL(DROP_TABLE + TABLE_MOMENTS);
        onCreate(db);
    }
}
