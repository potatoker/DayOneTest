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
public class InnerFragment1 extends BaseFragment {

    private static final String TAG = "InnerFragment1";

    public static InnerFragment1 newInstance(){
        return new InnerFragment1();
    }

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.inner_fragment1, null);
        }
        return mRootView;
    }

}
