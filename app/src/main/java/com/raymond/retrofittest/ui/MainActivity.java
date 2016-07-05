package com.raymond.retrofittest.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.DaysAdapter;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseDrawerActivity implements DataPresenter.GetDaysInterface{

    private static final String TAG = "MainActivity";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MEDIA_TYPE_IMAGE = 4;
    static final int MEDIA_TYPE_VIDEO = 5;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    protected Uri mMediaUri;

    private DaysAdapter daysAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        daysAdapter = new DaysAdapter();
        initView();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mMediaUri = Utils.getOutputMediaFileUri(EnvVariable.MEDIA_TYPE_IMAGE, MainActivity.this);

        if(mMediaUri == null){
            Toast.makeText(MainActivity.this, R.string.error_external_storage, Toast.LENGTH_LONG).show();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //这里居然之前忘记调用super.
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //之前这里搞错，以为data会返回uri,只有选择照片会返回uri,拍照的Intent中设置了MediaStore.EXTRA_OUTPUT
            //就不会返回uri，这里直接用之前预设的uri 就可以了
            PublishActivity.openWithPhotoUri(MainActivity.this, mMediaUri);
            Log.d(TAG, "result ok");

            //将此照片放入gallery
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(mMediaUri);
            sendBroadcast(mediaScanIntent);

        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initView(){

    }

    @Override
    public void onGetDays(List<OneDay> days, Boolean ok) {
        if(ok){
            daysAdapter.refill(days, true);
        }else{
            Toast.makeText(MainActivity.this, R.string.error_network, Toast.LENGTH_SHORT).show();
        }
    }
}
