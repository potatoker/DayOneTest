package com.raymond.retrofittest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.raymond.retrofittest.Message;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class PublishActivity extends BaseActivity {

    private static final String TAG = "PublishActivity";
    public static final String KEY_TAKEN_PHOTO_URI = "key_taken_photo_uri";

    private Uri photoUri;
    private int photoSize;

    @Bind(R.id.pub_pre_img)
    ImageView pub_pre_img;

    @Bind(R.id.save_to_current)
    Button saveButton;

    @Bind(R.id.save_to_fav)
    Button saveFava;

    @Bind(R.id.publish_position)
    EditText positionEditText;

    @Bind(R.id.pub_desc)
    EditText pubDesc;

    DatabaseManager dbm;

    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    AMapLocationListener mAMapLocationListener = null;


    public static void openWithPhotoUri(Activity openingActivity, Uri photoUri){
        Intent intent = new Intent(openingActivity, PublishActivity.class);
        intent.putExtra(KEY_TAKEN_PHOTO_URI, photoUri);
        openingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        dbm = new DatabaseManager();

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

        if(pub_pre_img == null ){
            Log.d(TAG, "preview img is null");
        }

        pub_pre_img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                pub_pre_img.getViewTreeObserver().removeOnPreDrawListener(this);
                loadImage();
                return true;
            }
        });

        initPostionService();

        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        File file = new File(photoUri.getPath());
                        file.delete();
                        if(file.exists()){
                            try {
                                file.getCanonicalFile().delete();
                                if (file.exists()) {
                                    getApplicationContext().deleteFile(file.getName());
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        PublishActivity.super.onBackPressed();

                    }
                }).create().show();
    }


    public void initPostionService(){
        mLocationClient = new AMapLocationClient(getApplicationContext());

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocationLatest(true);
        mAMapLocationListener = new AMapLocationListener(){
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    positionEditText.setText(amapLocation.getAddress());
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        };
        mLocationClient.setLocationListener(mAMapLocationListener);
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


    @OnClick(R.id.save_to_current)
    public void saveToCurrent(){
        OneDay currentDay;

        Log.d(TAG, "save click");

//        DatabaseManager.deletCurrentDay();

        currentDay = DatabaseManager.getCurrentDay();


        if (currentDay == null){
            OneDay day = new OneDay();
            day.setTime(Utils.getCurrentDate());
            day.setUserId(User.getInstance().getuId());
            day.setFlag(DatabaseHelper.FLAG_DAY_CURRENT);
//            day.setFlag(DatabaseHelper.FLAG_DAY_NO_COMMIT);

            Log.d(TAG, "get:" + day.getTime());
            DatabaseManager.addDay(day);
            currentDay = DatabaseManager.getCurrentDay();
        }



        Moment moment = new Moment();
        moment.setPhotoURL(photoUri.toString());
        moment.setLocation(positionEditText.getText().toString());
        moment.setDesc(pubDesc.getText().toString());

        moment.setDate(Utils.getCurrentTime());

        moment.setDayId(currentDay.getDayId());
        moment.setUid(User.getInstance().getuId());

        DatabaseManager.addMoment(moment);

//        EventBus.getDefault().post(new Message());

        finish();

    }

    @OnClick(R.id.save_to_fav)
    public void saveToFava(){

        Log.d(TAG, "fava click");

        Moment moment = new Moment();
        moment.setPhotoURL(photoUri.toString());
        moment.setDesc(pubDesc.getText().toString());
        moment.setDate(Utils.getCurrentTime());
        moment.setDayId(DatabaseManager.FAVADAY);
        moment.setFavaFlag(DatabaseHelper.FLAG_MOMENT_FAVA);
        moment.setLocation(positionEditText.getText().toString());
        moment.setUid(User.getInstance().getuId());
        moment.setMoment_snyc(Utils.getTimeStamp2());
        DatabaseManager.addMoment(moment);
        finish();
    }


}
