package com.raymond.retrofittest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;


/**
 * Created by raymond on 6/17/16.
 */
public class InnerFragment2 extends BaseFragment {

    private static final String TAG = "InnerFragment2";

    public static InnerFragment2 newInstance(){
        return new InnerFragment2();
    }

    private View mRootView;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        DaggerManager.component().inject(this);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.inner_fragement2, null);
        }
        return mRootView;
    }
}
