package com.raymond.retrofittest.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.DayShowAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DayShowActivity extends AppCompatActivity {

    public static final String KEY_DAY_ID="key_dayId";

    @Bind(R.id.showDay_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.dayShow_refresher)
    SwipeRefreshLayout swipeRefreshLayout;

    DayShowAdapter adapter;
    String dayId;

    List<Moment> moments = new ArrayList<>();

    public static void openWithDayId(Context openingActivity, String dayId){
        Intent intent = new Intent(openingActivity, DayShowActivity.class);
        intent.putExtra(KEY_DAY_ID, dayId);
        openingActivity.startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_show);
        ButterKnife.bind(this);

        dayId = getIntent().getStringExtra(KEY_DAY_ID);
//        moments = DatabaseManager.getMomentByDayId(dayId);

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDay();
            }
        });
        initRecyclerView();

    }

    public void initRecyclerView(){
        adapter = new DayShowAdapter(moments,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        loadDay();
    }

    public void loadDay(){
        swipeRefreshLayout.setRefreshing(true);
        List<Moment> list = DatabaseManager.getMomentByDayId(dayId);

        adapter.refill(list, true);
        swipeRefreshLayout.setRefreshing(false);
    }



}
