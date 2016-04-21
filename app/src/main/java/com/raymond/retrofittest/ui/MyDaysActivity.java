package com.raymond.retrofittest.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raymond.retrofittest.R;

public class MyDaysActivity extends BaseActivity {


    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_days);


    }
    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, MyDaysActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

}
