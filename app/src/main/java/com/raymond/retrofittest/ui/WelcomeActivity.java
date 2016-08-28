package com.raymond.retrofittest.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.TokenKeeper;

import junit.framework.Test;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    private boolean ifStartMainActivity = false;
    private TokenKeeper tk;

//    private boolean loadLayoutInfoComplete = false;

//    // Constants
//    // The authority for the sync adapter's content provider
//    public static final String AUTHORITY = "com.example.android.datasync.provider";
//    // An account type, in the form of a domain name
//    public static final String ACCOUNT_TYPE = "example.com";
//    // The account name
//    public static final String ACCOUNT = "dummyaccount";
//    // Instance fields
//    Account mAccount;

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

//        mAccount = CreateSyncAccount(this);


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(-1);
            }
        };

        timer.schedule(task, 1500);
    }


//    public static Account CreateSyncAccount(Context context) {
//        // Create the account type and default account
//        Account newAccount = new Account(
//                ACCOUNT, ACCOUNT_TYPE);
//        // Get an instance of the Android account manager
//        AccountManager accountManager =
//                (AccountManager) context.getSystemService(
//                        ACCOUNT_SERVICE);
//        /*
//         * Add the account and account type, no password or user data
//         * If successful, return the Account object, otherwise report an error.
//         */
//        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
//            /*
//             * If you don't set android:syncable="true" in
//             * in your <provider> element in the manifest,
//             * then call context.setIsSyncable(account, AUTHORITY, 1)
//             * here.
//             */
//        } else {
//            /*
//             * The account exists or some other error occurred. Log this, report it,
//             * or handle it internally.
//             */
//        }
//        return newAccount;
//    }


}
