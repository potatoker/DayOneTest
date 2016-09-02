package com.raymond.retrofittest.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

/**
 * Created by raymond on 8/30/16.
 */
public class DecodeBitmap extends AsyncTask<String, Integer, Bitmap> {

    public interface OnBitMapGet{
        void onBitMapGet(Bitmap bitmap);
    }

    public OnBitMapGet onBitMapGet;

    @Override
    protected Bitmap doInBackground(String... params) {
        String imgString = params[0];
        byte[] data = Base64.decode(imgString,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(data, 0, data.length);
        return decodedByte;
    }
    public void onPostExecute(Bitmap bitmap){
        onBitMapGet.onBitMapGet(bitmap);
    }

}
