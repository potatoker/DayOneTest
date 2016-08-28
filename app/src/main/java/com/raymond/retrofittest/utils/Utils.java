package com.raymond.retrofittest.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by raymond on 16/4/18.
 */

public class Utils {


    private static final String TAG = "Utils";

    public static Uri getOutputMediaFileUri(int mediaType, Context context) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (isExternalStorageAvailable()) {
            // get the URI

            // 1. Get the external storage directory
            String appName = context.getResources().getString(R.string.app_name);
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    appName);

            // 2. Create our subdirectory
            if (! mediaStorageDir.exists()) {
                if (! mediaStorageDir.mkdirs()) {
                    Log.e(TAG, "Failed to create directory.");
                    return null;
                }
            }

            // 3. Create a file name
            // 4. Create the file
            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;
            if (mediaType == EnvVariable.MEDIA_TYPE_IMAGE) {
                mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
            }
            else if (mediaType == EnvVariable.MEDIA_TYPE_VIDEO) {
                mediaFile = new File(path + "VID_" + timestamp + ".mp4");
            }
            else {
                return null;
            }

            Log.d(TAG, "File: " + Uri.fromFile(mediaFile));

            // 5. Return the file's URI
            return Uri.fromFile(mediaFile);
        }
        else {
            return null;
        }
    }


    public static File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        return image;
    }

    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else {
            return false;
        }
    }


    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    public static Date CastStringToDate(String sdate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        Date date = new Date();
        try{
            date = dateFormat.parse(sdate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    public static String getTimeStamp(){
        return Long.toString(System.currentTimeMillis());
    }

    public static int getTimeStamp2(){
        return (int)System.currentTimeMillis();
    }



    public static void loadImage(final ImageView imageView, Uri uri, Context context){

        int photoSize = context.getResources().getDimensionPixelSize(R.dimen.all_photo_size);

        imageView.setScaleX(0);
        imageView.setScaleY(0);

        Picasso.with(context)
                .load(uri)
                .centerCrop()
                .resize(photoSize, photoSize)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.animate()
                                .scaleX(1.f).scaleY(1.f)
                                .setInterpolator(new OvershootInterpolator())
                                .setDuration(300)
                                .start();

                    }

                    @Override
                    public void onError() {

                    }
                });

    }


    public static void loadImage(final ImageView imageView, Uri uri, Context context, int size){

//        imageView.setScaleX(0);
//        imageView.setScaleY(0);

        Picasso.with(context)
                .load(uri)
                .centerCrop()
                .resize(size, size)
                .into(imageView);
    }

    public static void loadImageFromNet(final ImageView imageView, String location, Context context, int size){
        Picasso.with(context)
                .load(location)
                .centerCrop()
                .resize(size, size)
                .into(imageView);
    }


    public static void deleteFileFromMediaStore(final ContentResolver contentResolver, final File file) {
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?", new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri,
                        MediaStore.Files.FileColumns.DATA + "=?", new String[]{absolutePath});
            }
        }
    }

    public static void deletePhotoByURI(String file_dj_path){
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + file_dj_path);
            } else {
                System.out.println("file not Deleted :" + file_dj_path);
            }
        }
    }


    public static int dpToPx(Context context,float dpValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public static void imageDownload(Context ctx, String url){

        String path = getOutputMediaFileUri(EnvVariable.MEDIA_TYPE_IMAGE, ctx).toString();
        Picasso.with(ctx)
                .load("http://blog.concretesolutions.com.br/wp-content/uploads/2015/04/Android1.png")
                .into(getTarget(url,path));
    }

    //target to save
    private static Target getTarget(final String url, final String path){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

//                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url);
                        File file = new File(path);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateString(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(dateString);
        }catch (ParseException e){
            e.printStackTrace();
        }


        SimpleDateFormat timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return timestamp.format(date);
    }


}
