package com.raymond.retrofittest.ui.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.DaysAdapter;
import com.raymond.retrofittest.adapters.FakePageAdapter;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseManager;

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

    @Bind(R.id.myzone_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.my_zone_refresher)
    SwipeRefreshLayout swipeRefreshLayout;

    DaysAdapter daysAdapter;

    List<OneDay> openDays;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_myzone, null);
        }

        ButterKnife.bind(this, mRootView);

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadOpenDays();
            }
        });

        initRecyclerView();
        return mRootView;
    }

    public void onResume(){
        super.onResume();
        loadOpenDays();
    }

    private void initRecyclerView() {
        daysAdapter = new DaysAdapter(openDays, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(daysAdapter);
    }

    private void loadOpenDays(){
        swipeRefreshLayout.setRefreshing(true);
        openDays = DatabaseManager.getOpenDays();

        Log.d(TAG, "opendays nums:"+ openDays.size());

        daysAdapter.refill(openDays, true);
        swipeRefreshLayout.setRefreshing(false);

    }


}