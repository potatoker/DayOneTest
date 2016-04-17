package com.raymond.retrofittest.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.raymond.retrofittest.Utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by raymond on 16/4/16.
 */
public class PostCustomRequest<T> extends Request<T> {

    private ResponseListener listener;
    private Gson gson;
    private Type type;
    private Map<String, String> params;

    public PostCustomRequest(String url,Map<String, String> params, Type type,
                             ResponseListener listener) {
        super(Method.POST, url, listener);
        this.listener = listener;
        this.type = type;
        this.params = params;
        this.gson = new Gson();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result;
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //获取http 头部中的charset信息（如用的是utf-8），用以解析byte成字符串
            result = gson.fromJson(StringUtils.fixJsonString(jsonString), type);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
            //这里关于后面这个cache的部分不太懂
        }catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

    }

//  parseNetWorkResponse
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    protected Map<String, String> getParams() throws AuthFailureError{
        return params;
    }
}
