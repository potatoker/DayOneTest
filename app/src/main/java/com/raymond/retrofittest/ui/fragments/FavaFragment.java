package com.raymond.retrofittest.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.FavaGridAdapter;
import com.raymond.retrofittest.adapters.GridAdapter;
import com.raymond.retrofittest.adapters.OneDayAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.User;

import com.raymond.retrofittest.db.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 6/14/16.
 */
public class FavaFragment extends BaseFragment {

    private static final String TAG = "FavaFragment";

    List<Moment> favaMoments = new ArrayList<>();

    FavaGridAdapter adapter;

    @Bind(R.id.fava_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.fava_refresher)
    SwipeRefreshLayout refreshLayout;


    View rootView;

    public static FavaFragment newInstance(){
        return new FavaFragment();
    }




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_fava, container, false);
        }

        ButterKnife.bind(this, rootView);

        refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

//        setRecyclerView();
        initRecyclerView();
        return rootView;
    }

//    private void setRecyclerView(){
//        adapter  = new OneDayAdapter(favaMoments, getActivity());
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//
//        refreshData();
//
//    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "fava??");
        refreshData();
    }


    private void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new FavaGridAdapter(favaMoments, getActivity());
        recyclerView.setAdapter(adapter);
        refreshData();
    }


    public void refreshData(){
        refreshLayout.setRefreshing(true);

        Log.d(TAG+"!!!!!!!S", User.getInstance().getuId());
        List<Moment> moments = DatabaseManager.getFavaMoments(User.getInstance().getuId());

        if(moments!=null){
            favaMoments = moments;
            Log.d(TAG, ""+moments.size());
        }


        adapter.refill(favaMoments, true);
        refreshLayout.setRefreshing(false);

    }


}
