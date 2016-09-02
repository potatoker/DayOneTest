package com.raymond.retrofittest.ui.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.raymond.retrofittest.MyApplication;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.TokenKeeper;
import com.raymond.retrofittest.service.AlarmReceiver;
import com.raymond.retrofittest.ui.LoginActivity;
import com.raymond.retrofittest.ui.MyNewDaysActivity;
import com.raymond.retrofittest.utils.Sysner;
import com.raymond.retrofittest.utils.Utils;
import com.raymond.retrofittest.views.NestedCoordinatorLayout;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by raymond on 6/5/16.
 */
public class MoreFragement extends BaseFragment {

    private static final String TAG = "MoreFragement";

    View mRootView;

    @Bind(R.id.settings_alarm)
    ToggleButton alarmButton;

    @Bind(R.id.settings_syns)
    ToggleButton synsBtton;

    @Bind(R.id.settings_logout)
    TextView logout;

    @Bind(R.id.settings_syns_byhand)
    TextView synsByHand;


    PendingIntent pendingIntent;
    TokenKeeper tokenKeeper;



    public static MoreFragement newInstance() {
        return new MoreFragement();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View rootView = inflater.inflate(R.layout.fragment_mydays, container, false);
//        ButterKnife.bind(this, rootView);
//        testText.setText("this is find more fragment");
//        return rootView;


        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_settings, null);
        }

        ButterKnife.bind(this, mRootView);

        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
        tokenKeeper = new TokenKeeper(MyApplication.getAppContext());
        initAlarmToggle();


        tokenKeeper = new TokenKeeper(getActivity().getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenKeeper.logoutUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return mRootView;
    }


    public void initAlarmToggle(){

        if(tokenKeeper.getAlarmOpen()){
            alarmButton.setChecked(true);
        }else{
            alarmButton.setChecked(false);
        }

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmButton.isChecked()){
                    tokenKeeper.setAlarmOpen(true);
                    openAlarm();

                }else{
                    tokenKeeper.setAlarmOpen(false);
                    cancelAlarm();

                }
            }
        });
    }


    public void openAlarm(){
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(getActivity(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.settings_syns_byhand)
    public void SynsByHand(){
        Toast.makeText(getActivity(), "start sync", Toast.LENGTH_SHORT).show();
        new Sysner(getActivity()).startSysn();
    }



}
