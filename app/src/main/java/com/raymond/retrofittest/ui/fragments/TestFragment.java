package com.raymond.retrofittest.ui.fragments;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.raymond.retrofittest.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by raymond on 6/17/16.
 */
public class TestFragment extends BaseFragment {

    private static final String TAG = "TestFragment";

    @Bind(R.id.text)
    TextView textView;

    public static TestFragment newInstance(){
        return new TestFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, rootView);

        Log.d(TAG, "test fragment get called");
        return rootView;
    }
}
