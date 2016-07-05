package com.raymond.retrofittest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;

/**
 * Created by raymond on 7/4/16.
 */
public class MessageFragment extends BaseFragment {



    public static MessageFragment newInstance(){
        return new MessageFragment();
    }

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_my_messages, null);
        }
        return mRootView;
    }
}
