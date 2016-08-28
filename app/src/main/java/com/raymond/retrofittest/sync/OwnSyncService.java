package com.raymond.retrofittest.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by raymond on 7/16/16.
 */
public class OwnSyncService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public OwnSyncService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {




    }




}
