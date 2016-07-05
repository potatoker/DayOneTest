package com.raymond.retrofittest.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.TokenKeeper;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    private boolean ifStartMainActivity = false;
    private TokenKeeper tk;

//    private boolean loadLayoutInfoComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Handler handler = new Handler(WelcomeActivity.this.getMainLooper()){
            public void handleMessage(Message msg) {

                if (msg.what == -1) {

                    if (!ifStartMainActivity) {
                        ifStartMainActivity = true;

                        tk = new TokenKeeper(getApplicationContext());
//                        tk.logoutUser();

                        if(!tk.isUserLoggedIn()){
//                            Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
                            Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
                            startActivity(it);
                            finish();
                        }else{
                            tk.getUser();
                            Intent it2 = new Intent(WelcomeActivity.this, MyNewDaysActivity.class);
                            startActivity(it2);
                            finish();
                        }
                    }
                }
            }
        };


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(-1);
            }
        };

        timer.schedule(task, 1500);
    }


}
