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
}
