package com.raymond.retrofittest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.ExploreAdapter;
import com.raymond.retrofittest.adapters.ExploreAdapter2;
import com.raymond.retrofittest.datatype.ExploreDay;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.ui.MyNewDaysActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 7/4/16.
 */
public class ExploreFragment extends BaseFragment implements DataPresenter.GetExploreDays{

    private static final String TAG = "ExploreFragment";

    public static ExploreFragment newInstance(){
        return new ExploreFragment();
    }

    private View mRootView;

    @Bind(R.id.explore_recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.explor_refresher)
    SwipeRefreshLayout swipeRefreshLayout;

    List<ExploreDay> exploreDays = new ArrayList<>();
    ExploreAdapter2 adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_explore, null);
        }


        ButterKnife.bind(this, mRootView);

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadExplores();
            }
        });

        initRecyclerView();

        return mRootView;
    }


    public void initRecyclerView(){
        adapter = new ExploreAdapter2(exploreDays, getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        loadExplores();
        MyNewDaysActivity.fab.attachToRecyclerView(recyclerView);
    }

    public void loadExplores(){
        swipeRefreshLayout.setRefreshing(true);

        DataPresenter.GetExploreDays(User.getInstance().getUid(),this);
    }

    @Override
    public void onGetExploreDays(List<ExploreDay> exploreDays, Boolean ok) {

        adapter.refill(exploreDays,true);
        swipeRefreshLayout.setRefreshing(false);

    }
}
