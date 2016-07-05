package com.raymond.retrofittest.ui;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;

import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.ui.fragments.FindFragment;
import com.raymond.retrofittest.ui.fragments.NavigationManager;
import com.raymond.retrofittest.utils.Utils;
import com.raymond.retrofittest.views.NestedCoordinatorLayout;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNewDaysActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "MyNewDaysActivity";
    public BottomBar mBottomBar;

    private int fragNum = 0;
    private int mMaxScrollSize;

    public FindFragment currentFragment=null;
    private AppBarLayout findAppBar;

    @Bind(R.id.drafts_fab)
    FloatingActionButton fab;

    @Bind(R.id.myScrollingContent)
    NestedScrollView nestedScrollView;

    public NestedCoordinatorLayout parentCoord;
    public NestedCoordinatorLayout childCoord;


    private NavigationManager navigationManager;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected Uri mMediaUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new_days_acitvity);
//        setContentView(R.layout.no_shy_main);

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        navigationManager = new NavigationManager(getSupportFragmentManager());

        nestedScrollView.setFillViewport(true);

        parentCoord = (NestedCoordinatorLayout) findViewById(R.id.myCoordinator);

        mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.myCoordinator),
                findViewById(R.id.myScrollingContent), savedInstanceState);

//        mBottomBar = BottomBar.attach(this, savedInstanceState);

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId){
                    case R.id.myDays:
                        navigationManager.startMydays();
//                        fab.hide();
                        fragNum = 0;
                        break;

                    case R.id.find:
                        currentFragment = navigationManager.startFind();

//                        findAppBar = currentFragment.getAppbarLayout();


//                        fab.hide();
                        fragNum = 1;


                        break;

                    case R.id.drafts:
                        navigationManager.startDrafts();
//                        fab.show();
                        fragNum = 2;
                        break;

                    case R.id.more:
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



//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        Moment moment = new Moment("fakeUrl", dateFormat.format(date), "fakeDesc", "fakeLocation");
//        DataPresenter.postMoment2(moment, "01", this);

//        deleteDatabase(DatabaseHelper.DB_NAME);
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (fragNum == 1) {
            if (mMaxScrollSize == 0)
                mMaxScrollSize = appBarLayout.getTotalScrollRange();

            if (Math.abs(verticalOffset) == mMaxScrollSize) {
                parentCoord.unlockNestedScrolling();
                Log.d(TAG, " ==: " + verticalOffset);

            } else if (Math.abs(verticalOffset) < mMaxScrollSize) {
                childCoord.unlockNestedScrolling();
                parentCoord.lockNestedScrolling();

                Log.d(TAG, "<:"+verticalOffset);
            }
        }
    }


    public AppBarLayout.OnOffsetChangedListener listener(){
        return this;
    }
}
