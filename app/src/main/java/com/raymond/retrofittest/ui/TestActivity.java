package com.raymond.retrofittest.ui;

import android.content.Intent;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.service.BitmapGetAsync;
import com.raymond.retrofittest.utils.DecodeImg;
import com.raymond.retrofittest.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements DataPresenter.PostMomentInterface2, BitmapGetAsync.AsyncResponse, DecodeImg.DecodeComp, DataPresenter.GetMoments

, DataPresenter.GetStamps{

    private static final String TAG = "TestActivity";

    @Bind(R.id.test_img)
    ImageView img;

    @Bind(R.id.test_img2)
    ImageView img2;

    List<Moment> favas;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);

        favas = DatabaseManager.getFavaMoments(User.getInstance().getUid());

        Log.d(TAG, "fava size:"+favas.size());

        for(int i=0;i<favas.size();i++){
            Log.d(TAG, "favas path:"+favas.get(i).getPhotoURL());
        }


        DataPresenter.GetMoments("01",this);

//        showFavas();

//        getImgString();

//        for(int i=0;i<favas.size();i++){
//            Log.d(TAG,"favas url:"+favas.get(i).getPhotoURL());
//        }

//        decodeAndEncodeTest();

    }

    @Override
    public void processFinish(Moment moment) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        Moment moment = new Moment("fakeUrl", dateFormat.format(date), "fakeDesc", "fakeLocation");
//        moment.setImgString(output);
//        DataPresenter.postMoment2(moment, "01", this);

        DataPresenter.postMoment2(moment,moment.getDayId(), this);

    }

    @Override
    public void onPostMoment(List<Moment> moments, Boolean ok) {
        if(moments!=null){
//            for(Moment moment : moments){
//
//                Log.d(TAG,"imgstring:"+moment.getImgString());
//                if(moment.getImgString()!=null && !moment.getImgString().equals(Moment.IMG_PIXEL_NULL) && !moment.getImgString().equals("")){
//                    Log.d(TAG, "in this to get image");
//
//                    String path = takeImg3(moment.getImgString());
//                    Log.d(TAG,path);
//                    moment.setPhotoURL(path);
//                }
//            }

            if(moments.size()==2){

                if(moments.get(0).getImgString()!=null && !moments.get(0).getImgString().equals(Moment.IMG_PIXEL_NULL) && !moments.get(0).getImgString().equals("")){
                    Log.d(TAG, "in this to get image");

                    String path = takeImg3(moments.get(0).getImgString(), moments.get(0).getDate());
                    Log.d(TAG,path);
                    moments.get(0).setPhotoURL(path);
                }

                Utils.loadImage(img, Uri.parse(moments.get(0).getPhotoURL()), this,300);

                if(moments.get(1).getImgString()!=null && !moments.get(1).getImgString().equals(Moment.IMG_PIXEL_NULL) && !moments.get(1).getImgString().equals("")){
                    Log.d(TAG, "in this to get image");

                    String path = takeImg3(moments.get(1).getImgString(), moments.get(1).getDate());
                    Log.d(TAG,path);
                    moments.get(1).setPhotoURL(path);
                }

                Utils.loadImage(img2, Uri.parse(moments.get(1).getPhotoURL()), this,300);


            }else{
                Log.d(TAG,"moment size:"+moments.size());
            }
        }
    }


    public String takeImg(String imgString){
        String path = null;
        byte[] data = Base64.decode(imgString,Base64.DEFAULT);
        try{
            File file = Utils.createImageFile();
            path = file.getAbsolutePath();
            OutputStream stream = new FileOutputStream(Utils.createImageFile());
            stream.write(data);
        }catch(Exception e){
           e.printStackTrace();
        }finally {
            return path;
        }
    }

    public String takeImg2(String imgString){

        Uri uri = Utils.getOutputMediaFileUri(EnvVariable.MEDIA_TYPE_IMAGE,this);
        byte[] data = Base64.decode(imgString,Base64.DEFAULT);
        try{
            File file = new File(uri.toString());
            file.createNewFile();
            OutputStream stream = new FileOutputStream(file);
            stream.write(data);
            stream.close();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(uri);
            sendBroadcast(mediaScanIntent);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            return uri.toString();
        }
    }



    public String takeImg3(String imgString, String date){
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/oneday/");
//        String filename = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime()) + ".jpg";

        String filename = Utils.convertDateString(date)+".jpg";

        Log.d(TAG,"filename:"+filename);

        File myFile=null;
        try {
            if (!folder.exists()) {
                folder.mkdirs();
                System.out.println("Making dirs");
            }
            myFile = new File(folder.getAbsolutePath(), filename);
            myFile.createNewFile();

            FileOutputStream out = new FileOutputStream(myFile);

            byte[] data = Base64.decode(imgString,Base64.DEFAULT);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(data, 0, data.length);

            if(decodedByte!=null){
                Log.d(TAG, "decode bitmap not null");
                img.setImageBitmap(decodedByte);
            }else{
                Log.d(TAG,"decode bitmap null");
            }

            out.write(data);
            out.flush();
            out.close();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(myFile));
            sendBroadcast(mediaScanIntent);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            if(myFile!=null){
                return Uri.fromFile(myFile).toString();
            }else
                return null;
        }

    }


    public void getImgString(){
//        for(Moment moment: favas){
//            Log.d(TAG,moment.getPhotoURL());
//            BitmapGetAsync async = new BitmapGetAsync(moment,this);
//            async.delegate = this;
//            async.execute(moment);
//        }

        for(Moment moment:favas){
            DecodeImg decodeImg = new DecodeImg(moment, this);
            decodeImg.decodeComp = this;
            decodeImg.decode();
        }
    }


    @Override
    public void onDecodeFinish(Moment moment) {
        DataPresenter.postMoment2(moment,moment.getDayId(), this);
    }

    public void serverTest(){
        for(Moment moment:favas) {
            DataPresenter.postMoment2(moment, moment.getDayId(), this);
        }
    }




    public void decodeAndEncodeTest(){
        String path = "file:///storage/emulated/0/Pictures/RetrofitTest/IMG_20160818_100039.jpg";
        Utils.loadImage(img, Uri.parse(path), this, 100);
    }

    public void showFavas(){
        Utils.loadImage(img, Uri.parse(favas.get(0).getPhotoURL()), this,300);
        Utils.loadImage(img2, Uri.parse(favas.get(1).getPhotoURL()), this,300);


    }

    @Override
    public void onGetMoments(List<Moment> moments, Boolean ok) {
        if(moments!=null){
//            for(Moment moment : moments){
//
//                Log.d(TAG,"imgstring:"+moment.getImgString());
//                if(moment.getImgString()!=null && !moment.getImgString().equals(Moment.IMG_PIXEL_NULL) && !moment.getImgString().equals("")){
//                    Log.d(TAG, "in this to get image");
//
//                    String path = takeImg3(moment.getImgString());
//                    Log.d(TAG,path);
//                    moment.setPhotoURL(path);
//                }
//            }

            if(moments.size()==2){

                if(moments.get(0).getImgString()!=null && !moments.get(0).getImgString().equals(Moment.IMG_PIXEL_NULL) && !moments.get(0).getImgString().equals("")){
                    Log.d(TAG, "in this to get image");

                    String path = takeImg3(moments.get(0).getImgString(), moments.get(0).getDate());
                    Log.d(TAG,"get moments!!!!!!!"+path);
                    moments.get(0).setPhotoURL(path);
                }

                Utils.loadImage(img, Uri.parse(moments.get(0).getPhotoURL()), this,300);

                if(moments.get(1).getImgString()!=null && !moments.get(1).getImgString().equals(Moment.IMG_PIXEL_NULL) && !moments.get(1).getImgString().equals("")){
                    Log.d(TAG, "in this to get image");

                    String path = takeImg3(moments.get(1).getImgString(),moments.get(1).getDate());
                    Log.d(TAG,"get moments!!!!!!!!"+path);
                    moments.get(1).setPhotoURL(path);
                }

                Utils.loadImage(img2, Uri.parse(moments.get(1).getPhotoURL()), this,300);


            }else{
                Log.d(TAG,"moment size:"+moments.size());
            }
        }
    }

    @Override
    public void onGetStamps(List<String> dates, Boolean ok) {


    }
}
