package com.raymond.retrofittest.net;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.adapters.RVAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.tools.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by raymond on 16/4/7.
 */
public class DayInfoLoader {
    private static final String TAG = "DayInfoLoader";
    private RVAdapter mAdapter;
//    private List<Moment> momentList;
    private String dayId;
    private String userId;
    private Integer mOffset = 0;
    private Boolean isLoading = false;

//    public void setMomentList(List<Moment> momentList){
//        this.momentList = momentList;
//    }

    public DayInfoLoader(String dayId, String userId, RVAdapter adapter) {

        this.dayId = dayId;
        this.mAdapter = adapter;
    }

    public void startLoad(){
        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put(EnvVariable.HEADER_USERID, User.getInstance().getuId());
//        headers.put("Accept", "*/*");//接受各种媒体类型

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                EnvVariable.SERVER_URL + EnvVariable.MOMENT_PATH + "?dayId=" + dayId +
//                        "&userID=" + userId, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d(TAG, "response: "+ response);
//                for(int i = 0; i < response.length(); i++){
//                    Moment moment
//                }
//
//            }
//
//        },
//                new Response.ErrorListener(){
//                    public void onErrorResponse(VolleyError error){
//
//                    }
//        });

        StringRequest oneDayRequest = new StringRequest(Request.Method.GET,
                EnvVariable.SERVER_URL + EnvVariable.MOMENT_PATH,
                new Response.Listener<String>(){
                        public void onResponse(String response){
                            Log.d(TAG, "response: "+ response);
                            try {
                                Gson gson = new Gson();
                                JSONObject resJson = new JSONObject(response);
                                String strRes = resJson.getString(EnvVariable.RESPONSE_RESULT);

                                if((strRes.equals(EnvVariable.RESPONSE_RESULT_OK)) &&
                                        (resJson.has(EnvVariable.DATA))){
                                    Type type = new TypeToken<ArrayList<Moment>>(){}.getType();//获取泛型类type的方式
                                    ArrayList<Moment> responseList = gson.fromJson(
                                            resJson.get(EnvVariable.DATA).toString(), type);

                                    if(mOffset == 0){
                                        mAdapter.refill(responseList, true);
                                    }else{
                                        mAdapter.refill(responseList, false);
                                    }
                                    mOffset = mAdapter.getItemCount();
//                                    isLoading = false;

                                    Log.d(TAG, "loaded " + responseList.size() + "items");
                                }

                            }catch (Exception e){
                                Log.d(TAG, "exception: "+ e.getLocalizedMessage());
                            }

                        }
            }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                if((error != null) && (error.networkResponse != null)
                        && (error.networkResponse.data != null))
                    Log.d(TAG, "error: " + new String(error.networkResponse.data));
            }

        });

        VolleySingleton.getInstance().addToRequestQueue(oneDayRequest);

    }

}
