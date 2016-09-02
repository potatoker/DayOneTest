package com.raymond.retrofittest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.raymond.retrofittest.datatype.Moment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by raymond on 8/18/16.
 */
public class DecodeImg {

    private static final String TAG = "DecodeImg";

    Context context;
    Moment moment;

    public DecodeImg(Moment moment, Context context){
        this.context = context;
        this.moment = moment;
    }

    public interface DecodeComp{
        void onDecodeFinish(Moment moment);
    }

    public DecodeComp decodeComp;

    public void decode(){

        Picasso.with(context)
                .load(moment.getPhotoURL())
                .resize(300,300)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                /* Save the bitmap or do something with it here */

                        //Set it in the ImageView
                        moment.setImgString(Utils.getStringImage(bitmap));
                        decodeComp.onDecodeFinish(moment);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d(TAG, "fail");
                    }
                });
    }
}
