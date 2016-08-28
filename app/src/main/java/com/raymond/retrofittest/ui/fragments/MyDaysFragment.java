package com.raymond.retrofittest.ui.fragments;


import android.os.Bundle;
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
import com.raymond.retrofittest.adapters.NewDaysAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.ui.MyNewDaysActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 6/5/16.
 */
public class MyDaysFragment extends BaseFragment {

    private static final String TAG = "MyDaysFragment";

    @Bind(R.id.my_days_recycler)
    RecyclerView recyclerView;

    @Bind(R.id.my_days_fresher)
    SwipeRefreshLayout swipeRefreshLayout;

    NewDaysAdapter daysAdapter;

    List<OneDay> commitDays;

    View rootView;

    public static MyDaysFragment newInstance() {
        return new MyDaysFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_mydays, container, false);
        }
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCommitDays();
            }
        });

        initView();
        return rootView;
    }

    public void onResume(){
        super.onResume();
        Log.d(TAG, "load my days?");
//
        loadCommitDays();
    }

    public void initView(){

        daysAdapter = new NewDaysAdapter(commitDays, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(daysAdapter);
        loadCommitDays();

        MyNewDaysActivity.fab.attachToRecyclerView(recyclerView);

//        Log.d(TAG,"initView ok");
    }


    public void loadCommitDays(){

//        Log.d(TAG, "log here");
        swipeRefreshLayout.setRefreshing(true);
        commitDays = DatabaseManager.getAllDays();

        if(commitDays == null){
//            Log.d(TAG,"commit days null");
        }
        else{
            Log.d(TAG, "commit days size:" + commitDays.size());
            for(OneDay day: commitDays){

                Log.d(TAG, "this day access: "+day.getAccess());

                for(Moment moment: day.getMoments()){
//                    Log.d(TAG, moment.getId());
//                    Log.d(TAG, moment.getPhotoURL());

                }
            }

        }

        daysAdapter.refill(commitDays, true);
        swipeRefreshLayout.setRefreshing(false);
    }

}
