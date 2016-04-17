package com.raymond.retrofittest.net;

import com.android.volley.Response;

/**
 * Created by raymond on 16/4/16.
 */
public interface ResponseListener<T> extends Response.ErrorListener, Response.Listener<T>{
}
