package com.raymond.retrofittest.adapters;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.roughike.bottombar.BottomBar;

/**
 * Created by raymond on 7/29/16.
 */
public class ScrollingFABBehavior extends FloatingActionButton.Behavior {


    private static final String TAG = "ScrollingFABBehavior";

    public ScrollingFABBehavior(Context context, AttributeSet attrs) {
        super();
        // Log.e(TAG, "ScrollAwareFABBehavior");
    }


    public boolean onStartNestedScroll(CoordinatorLayout parent, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {

        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (dependency instanceof RecyclerView)
            return true;

        return false;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               FloatingActionButton child, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // TODO Auto-generated method stub
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed);
        //Log.e(TAG, "onNestedScroll called");
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            //   Log.e(TAG, "child.hide()");
            child.hide();
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            //  Log.e(TAG, "child.show()");
            child.show();
        }
    }}