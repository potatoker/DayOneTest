package com.raymond.retrofittest.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.raymond.retrofittest.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class PublishActivity extends AppCompatActivity {

    public static final String KEY_TAKEN_PHOTO_URI = "key_taken_photo_uri";

    private Uri photoUri;
    private int photoSize;
    @Bind(R.id.pub_pre_img)
    ImageView pub_pre_img;


    public static void openWithPhotoUri(Activity openingActivity, Uri photoUri){
        Intent intent = new Intent(openingActivity, PublishActivity.class);
        intent.putExtra(KEY_TAKEN_PHOTO_URI, photoUri);
        openingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        photoSize = getResources().getDimensionPixelSize(R.dimen.all_photo_size);

        if(savedInstanceState == null){
            photoUri = getIntent().getParcelableExtra(KEY_TAKEN_PHOTO_URI);
        }else{
            photoUri = savedInstanceState.getParcelable(KEY_TAKEN_PHOTO_URI);
        }

        //这里只知道这是一个在调用pub_pre_img的OnDraw()函数之前(也是就是在OnMeasure之后，所以
        // 看到网上有例子说，如果想要在onCreate函数里获得某个view的高宽，需要调用这个，因为该onMeasure
        // 方法在onCreate执行完后才会调用，这个是后其高宽属性才会被设置)会调用的,但是这里的动作
        //是加载一个图片，为什么需要用这样一个回调函数呢
        pub_pre_img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pub_pre_img.getViewTreeObserver().removeOnPreDrawListener(this);
                loadImage();
                return true;
            }
        });



    }

    private void loadImage(){
        pub_pre_img.setScaleX(0);
        pub_pre_img.setScaleY(0);

        Picasso.with(this)
                .load(photoUri)
                .centerCrop()
                .resize(photoSize, photoSize)
                .into(pub_pre_img, new Callback() {
                    @Override
                    public void onSuccess() {
                        pub_pre_img.animate()
                                .scaleX(1.f).scaleY(1.f)
                                .setInterpolator(new OvershootInterpolator())
                                .setDuration(400)
                                .setStartDelay(200)
                                .start();
                    }

                    @Override
                    public void onError() {

                    }
                });

    }



}
