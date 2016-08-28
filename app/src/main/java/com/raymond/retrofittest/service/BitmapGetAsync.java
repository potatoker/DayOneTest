package com.raymond.retrofittest.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by raymond on 8/17/16.
 */
public class BitmapGetAsync extends AsyncTask<Moment, Integer, String> {
    private static final String TAG = "BitmapGetAsync";

    private Moment moment;
    private Context context;

    public interface AsyncResponse {
        void processFinish(Moment output);
    }

    public BitmapGetAsync(Moment moment, Context context){
        this.moment = moment;
        this.context=context;
    }

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(Moment... params) {
        Log.d(TAG, params[0].getPhotoURL());
        String path = params[0].getPhotoURL();
        final Bitmap mbitmap;
        Picasso.with(context)
                .load(path)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                /* Save the bitmap or do something with it here */

                        //Set it in the ImageView
                        moment.setImgString(Utils.getStringImage(bitmap));
                        delegate.processFinish(moment);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }
                });
        return "ok";

    }

    public void onPostExecute(String v){
//        moment.setImgString(Utils.getStringImage(bitmap));
//        delegate.processFinish(moment);
    }

}
