package com.raymond.retrofittest.ui.fragments;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;

import com.raymond.retrofittest.adapters.DayAdapterWithFooter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.ui.MyNewDaysActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 6/14/16.
 */
public class CurrentDayFragment extends BaseFragment {



    @Bind(R.id.current_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.current_refresher)
    SwipeRefreshLayout refreshLayout;

    DayAdapterWithFooter adapter;
    View rootView;

    List<Moment> current_day = new ArrayList<>();

    public static CurrentDayFragment newInstance(){
        return new CurrentDayFragment();
    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public void onResume(){
        super.onResume();
        loadCurrentDay();
    }

    public void onPause(){
        super.onPause();
        loadCurrentDay();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_current, container, false);
        }

        ButterKnife.bind(this, rootView);

        refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCurrentDay();
            }
        });

        setRecyclerView(recyclerView);
        return rootView;
    }

    private void setRecyclerView(RecyclerView recyclerView){
        adapter = new DayAdapterWithFooter(current_day, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadCurrentDay();
        MyNewDaysActivity.fab.attachToRecyclerView(recyclerView);

    }

    private void loadCurrentDay(){
        refreshLayout.setRefreshing(true);
        OneDay day = DatabaseManager.getCurrentDayWithMoments();
        if(day == null){
            current_day = null;
        }
        else{
            current_day = day.getMoments();
        }

        adapter.refill(current_day, true);
        refreshLayout.setRefreshing(false);
    }


}
