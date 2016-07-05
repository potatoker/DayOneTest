package com.raymond.retrofittest.db;

import android.provider.BaseColumns;

/**
 * Created by raymond on 5/30/16.
 */
public final class DBDays {

    public DBDays(){}

    public static abstract class DBDaysEntry implements BaseColumns{
        public static final String TABLE_DAYS  = "table_days";

        public static final String DAY_ID = "day_id";
        public static final String DAY_TIME = "day_time";
        public static final String DAY_USER = "day_user";
    }
}
