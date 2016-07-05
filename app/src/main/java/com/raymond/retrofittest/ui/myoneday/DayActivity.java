package com.raymond.retrofittest.ui.myoneday;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;

import com.raymond.retrofittest.datatype.Moment;

import com.raymond.retrofittest.db.DatabaseManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DayActivity extends AppCompatActivity{

    @Bind(R.id.my_detail_recycler_view)
    RecyclerView rv;

    @Bind(R.id.my_days_fresher)
    SwipeRefreshLayout swipeRefreshLayout;

    private DetailDayAdapter detailDayAdapter;
    private ArrayList<Moment> momentList = new ArrayList<>();//不至于是null以生成adapter，后面会在网络中更新

    private String dayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        dayId = intent.getStringExtra(EnvVariable.DAY_ID);


        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDetailDay();
            }
        });

        setRv(rv);
    }

    public void onResume(){
        super.onResume();
        loadDetailDay();
    }


    public void setRv(RecyclerView rv){
        detailDayAdapter = new DetailDayAdapter(momentList, DayActivity.this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(DayActivity.this);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(manager);
        rv.setAdapter(detailDayAdapter);
        loadDetailDay();
    }

    public void loadDetailDay(){
        swipeRefreshLayout.setRefreshing(true);
        momentList = DatabaseManager.getMomentByDayId(dayId);
        detailDayAdapter.refill(momentList, true);
        swipeRefreshLayout.setRefreshing(false);
    }

}
