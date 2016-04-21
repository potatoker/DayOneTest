package com.raymond.retrofittest.utils;

/**
 * Created by raymond on 16/4/16.
 */
public class StringUtils {

    public static String fixJsonString(String jsonString){
        int startIndex = jsonString.indexOf('{');
        int endIndex = jsonString.lastIndexOf('}');
        return jsonString.substring(startIndex, endIndex + 1);
    }
}
