package com.raymond.retrofittest.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by raymond on 16/4/18.
 */

public class Utils {

    private static final String TAG = "Utils";

    public static Uri getOutputMediaFileUri(int mediaType, Context context) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (isExternalStorageAvailable()) {
            // get the URI

            // 1. Get the external storage directory
            String appName = context.getResources().getString(R.string.app_name);
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    appName);

            // 2. Create our subdirectory
            if (! mediaStorageDir.exists()) {
                if (! mediaStorageDir.mkdirs()) {
                    Log.e(TAG, "Failed to create directory.");
                    return null;
                }
            }

            // 3. Create a file name
            // 4. Create the file
            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;
            if (mediaType == EnvVariable.MEDIA_TYPE_IMAGE) {
                mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
            }
            else if (mediaType == EnvVariable.MEDIA_TYPE_VIDEO) {
                mediaFile = new File(path + "VID_" + timestamp + ".mp4");
            }
            else {
                return null;
            }

            Log.d(TAG, "File: " + Uri.fromFile(mediaFile));

            // 5. Return the file's URI
            return Uri.fromFile(mediaFile);
        }
        else {
            return null;
        }
    }

    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else {
            return false;
        }
    }


}
