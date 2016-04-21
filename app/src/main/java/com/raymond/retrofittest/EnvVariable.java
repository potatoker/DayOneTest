package com.raymond.retrofittest;

import java.util.ArrayList;

/**
 * Created by raymond on 16/3/12.
 */
public class EnvVariable {

    public final static String SERVER_URL = "http://120.24.217.50";
   // public final static String GET_DAY_IMG_URL = SERVER_URL + "/"
    public final static ArrayList<String> TEST_IMG_UTL = new ArrayList<String>(){
       {
           add("https://img1.doubanio.com/view/photo/photo/public/p1937781214.jpg");
           add("https://img1.doubanio.com/view/photo/photo/public/p1937781262.jpg");
           add("https://img1.doubanio.com/view/photo/photo/public/p1937781138.jpg");
       }
   };

    public final static String HEADER_USERID = "userid";

    public final static String MOMENT_PATH = "";
    public final static String RESPONSE_RESULT = "result";
    public final static String RESPONSE_RESULT_OK = "OK";
    public final static String DATA = "data";

    //network interface constants for param names
    public final static String DAY_ID = "day_id";
    public final static String USER_ID = "user_id";

    //network interface constants for server apis
    public final static String API_REQUEST_ONE_DAY = SERVER_URL + "bla";


    //special value for instinct
    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    //all the photo should be the same size to display?


}
