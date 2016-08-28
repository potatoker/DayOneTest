package com.raymond.retrofittest.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewImageActivity extends AppCompatActivity {


    private static final String TAG = "ViewImageActivity";

    public static final String KEY_MOMENT_ID = "moment_id";
    public static final String KEY_MOMENT_URL = "moment_url";
    public static final String KEY_MOMETN_DESC = "moment_desc";



    @Bind(R.id.view_img)
    ImageView img;

    @Bind(R.id.view_textview)
    TextView textView;

    String momentId;
//    Moment moment;
    String momentUrl;
    String momentDesc;


    public static void openWithMomentId(Context openingActivity, String momentId, String moment_url, String moment_desc){
        Intent intent = new Intent(openingActivity, ViewImageActivity.class);
        intent.putExtra(KEY_MOMENT_ID, momentId);
        intent.putExtra(KEY_MOMENT_URL, moment_url);
        intent.putExtra(KEY_MOMETN_DESC, moment_desc);
        openingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ButterKnife.bind(this);



        momentId = getIntent().getStringExtra(KEY_MOMENT_ID);
//        moment = DatabaseManager.getMomentById(momentId);
        momentUrl = getIntent().getStringExtra(KEY_MOMENT_URL);
        momentDesc = getIntent().getStringExtra(KEY_MOMETN_DESC);
//        Log.d(TAG, "url is :"+moment.getPhotoURL());


//        Picasso.with(this).load(Uri.parse(momentUrl)).noFade().into(img);
        Glide.with(this).load(Uri.parse(momentUrl)).into(img);

        textView.setText(momentDesc);

    }
}
