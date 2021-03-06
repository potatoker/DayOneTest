package com.raymond.retrofittest.ui.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.DaysAdapter;

import com.raymond.retrofittest.adapters.MyZoneAdapter;
import com.raymond.retrofittest.adapters.NewDaysAdapter;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.ui.MyNewDaysActivity;
import com.raymond.retrofittest.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by raymond on 7/4/16.
 */
public class MyzoneFragment extends BaseFragment{
    private static final String TAG = "MyzoneFragment";

    public static MyzoneFragment newInstance(){
        return new MyzoneFragment();
    }

    private View mRootView;

//    @Bind(R.id.myzone_recycler_view)
//    RecyclerView recyclerView;

    RecyclerView recyclerView;


    SwipeRefreshLayout swipeRefreshLayout;

    MyZoneAdapter daysAdapter;

    List<OneDay> openDays = new ArrayList<>();

    Boolean didUserTouchRecycler=false;
    Boolean hasAnimatedDown=false;
    Boolean hasAnimatedUp=false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_myzone, null);
        }
//
//        MyNewDaysActivity.parentCoord.lockNestedScrolling();

        swipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.my_zone_refresher);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadOpenDays();
            }
        });

        recyclerView = (RecyclerView) mRootView.findViewById(R.id.myzone_recycler_view);

        initRecyclerView();
        return mRootView;
    }

    public void onResume(){
        super.onResume();
        loadOpenDays();
    }

    private void initRecyclerView() {
        daysAdapter = new MyZoneAdapter(openDays,null,getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(daysAdapter);
//
//
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                didUserTouchRecycler = true;
//                return false;
//            }
//
//
//        });
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    // Scrolling up
//                    if (didUserTouchRecycler && !hasAnimatedDown) {
//                        MyNewDaysActivity.mBottomBar.animate().translationY(Utils.dpToPx(getActivity(),100));
//                        hasAnimatedDown = true;
//                        hasAnimatedUp = false;
//                        Log.e("scroll", "up");
//                    }
//                } else {
//                    // Scrolling down
//                    if (didUserTouchRecycler && !hasAnimatedUp) {
//                        MyNewDaysActivity.mBottomBar.animate().translationY(Utils.dpToPx(getActivity(), 0));
//                        hasAnimatedUp = true;
//                        hasAnimatedDown = false;
//                        Log.e("scroll", "down");
//                    }
//                }
//            }
//
//        });

        loadOpenDays();

        MyNewDaysActivity.fab.attachToRecyclerView(recyclerView);
    }

    private void loadOpenDays(){
        swipeRefreshLayout.setRefreshing(true);
        openDays = DatabaseManager.getOpenDays();

        if(openDays == null){
            Log.d(TAG, "opendays null");
        }

        Log.d(TAG, "opendays nums:"+ openDays.size());

        if(daysAdapter == null){
            Log.d(TAG, "addapter null");
            return;
        }

        daysAdapter.refill(openDays, true);
        swipeRefreshLayout.setRefreshing(false);

    }
}
