package com.raymond.retrofittest.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.melnykov.fab.FloatingActionButton;
import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;

import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.service.AlarmReceiver;
import com.raymond.retrofittest.service.BitmapGetAsync;
import com.raymond.retrofittest.ui.fragments.FindFragment;
import com.raymond.retrofittest.ui.fragments.NavigationManager;
import com.raymond.retrofittest.utils.Utils;
import com.raymond.retrofittest.views.NestedCoordinatorLayout;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;


import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNewDaysActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final String TAG = "MyNewDaysActivity";
    public static BottomBar mBottomBar;

    private int fragNum = 0;
    private int mMaxScrollSize;

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;



    public FindFragment currentFragment=null;
    private AppBarLayout findAppBar;

//    @Bind(R.id.drafts_fab)
//    FloatingActionButton fab;

    public static FloatingActionButton fab;

//    @Bind(R.id.myScrollingContent)
//    NestedScrollView nestedScrollView;

    public static CoordinatorLayout parentCoord;
    public NestedCoordinatorLayout childCoord;


    private NavigationManager navigationManager;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected Uri mMediaUri;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new_days_acitvity);
//        setContentView(R.layout.no_shy_main);

        ButterKnife.bind(this);

        NestedScrollView nestedScrollView = (NestedScrollView)findViewById(R.id.myScrollingContent);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        navigationManager = new NavigationManager(getSupportFragmentManager());

        nestedScrollView.setFillViewport(true);

        parentCoord = (CoordinatorLayout) findViewById(R.id.myCoordinator);

        mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.myCoordinator),
                findViewById(R.id.myScrollingContent), savedInstanceState);

//        mBottomBar = BottomBar.attach(this, savedInstanceState);

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.myDays:
//                        parentCoord.unlockNestedScrolling();
                        navigationManager.startMydays();
//                        fab.hide();
                        fragNum = 0;
                        break;

                    case R.id.find:
//                        parentCoord.lockNestedScrolling();
                        navigationManager.startFind();


//                        findAppBar = currentFragment.getAppbarLayout();


//                        fab.hide();
                        fragNum = 1;


                        break;

                    case R.id.drafts:
//                        parentCoord.unlockNestedScrolling();
                        navigationManager.startDrafts();
//                        fab.show();
                        fragNum = 2;
                        break;

                    case R.id.more:
//                        parentCoord.unlockNestedScrolling();
                        navigationManager.startMore();
//                        fab.hide();
                        fragNum = 3;
                        break;
                }

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });


        mBottomBar.mapColorForTab(0, "#495A62");
        mBottomBar.mapColorForTab(1, "#31333F");
        mBottomBar.mapColorForTab(2, "#8A6B63");
        mBottomBar.mapColorForTab(3, "#694A43");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }




//        deleteDatabase(DatabaseHelper.DB_NAME);


//        mBottomBar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//                if(top < oldTop && fab.isShown()){
//                    fab.hide();
//                    Log.d(TAG,"animate in");
//                }else if(top > oldTop && !fab.isShown()){
//                    fab.show();
//                    Log.d(TAG, "animate out");
//                }
//
//            }
//        });

//        mLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation amapLocation) {
//                if (amapLocation != null) {
//                    if (amapLocation.getErrorCode() == 0) {
////可在其中解析amapLocation获取相应内容。
//                        String address = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//
//                        String city = amapLocation.getDistrict();//城区信息
//                        String street = amapLocation.getStreet();//街道信息
//
//                        Log.d(TAG, address);
//                        Log.d(TAG,city);
//                        Log.d(TAG, street);
//
//                        Log.d(TAG,"??????????????????");
//
//
//                    }else {
//                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
//                        Log.e("AmapError","location Error, ErrCode:"
//                                + amapLocation.getErrorCode() + ", errInfo:"
//                                + amapLocation.getErrorInfo());
//                    }
//                }
//            }
//        };


        Intent in = getIntent();

        if(in !=null) {

            String text = in.getStringExtra(AlarmReceiver.KEY_COMMAND);

            if(text!=null) {

                if (text.equals(AlarmReceiver.COMMAND_TAKE_PHOTO)) {
                    dispatchTakePictureIntent();
                }
            }
        }

    }





//    public void setUpBottomBar(CoordinatorLayout coordinatorLayout){
//        mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.myCoordinator),
//                findViewById(R.id.myScrollingContent), savedInstanceState);
//    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //这里居然之前忘记调用super.
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //之前这里搞错，以为data会返回uri,只有选择照片会返回uri,拍照的Intent中设置了MediaStore.EXTRA_OUTPUT
            //就不会返回uri，这里直接用之前预设的uri 就可以了
            PublishActivity.openWithPhotoUri(MyNewDaysActivity.this, mMediaUri);
            Log.d(TAG, "result ok");

            //将此照片放入gallery
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(mMediaUri);
            sendBroadcast(mediaScanIntent);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mMediaUri = Utils.getOutputMediaFileUri(EnvVariable.MEDIA_TYPE_IMAGE, MyNewDaysActivity.this);

        if(mMediaUri == null){
            Toast.makeText(MyNewDaysActivity.this, R.string.error_external_storage, Toast.LENGTH_LONG).show();
        }
        else {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
            Log.d(TAG, "uri is : " + mMediaUri.toString());
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                //用这个判断是否有activity可以handle这个拍照事件，防止没有的情况下程序崩溃
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
//
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

//        if (fragNum == 1) {
//            if (mMaxScrollSize == 0)
//                mMaxScrollSize = appBarLayout.getTotalScrollRange();
//
//            if (Math.abs(verticalOffset) == mMaxScrollSize) {
//                parentCoord.unlockNestedScrolling();
//                Log.d(TAG, " ==: " + verticalOffset);
//
//            } else if (Math.abs(verticalOffset) < mMaxScrollSize) {
//                childCoord.unlockNestedScrolling();
//                parentCoord.lockNestedScrolling();
//
//                Log.d(TAG, "<:"+verticalOffset);
//            }
//        }
    }



    public AppBarLayout.OnOffsetChangedListener listener(){
        return this;
    }



}
