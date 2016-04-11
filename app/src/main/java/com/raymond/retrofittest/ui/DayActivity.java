package com.raymond.retrofittest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.RVAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.net.DayInfoLoader;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayActivity extends AppCompatActivity {


    private RecyclerView rv;
    private RVAdapter mRVAdapter;
    private List<Moment> momentList;
    private DayInfoLoader dayInfoLoader;
    private String dayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Intent intent = getIntent();
        dayId = intent.getStringExtra(EnvVariable.DAY_ID);

        rv = (RecyclerView)findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        mRVAdapter = new RVAdapter(momentList);
        rv.setAdapter(mRVAdapter);

        dayInfoLoader = new DayInfoLoader(dayId, User.getInstance().getuId(), mRVAdapter);
        dayInfoLoader.startLoad();
    }

    private void initializeData(){
        momentList = new ArrayList<>();
        momentList.add(new Moment("01", EnvVariable.TEST_IMG_UTL.get(0), new Date(), "lala"));
        momentList.add(new Moment("02", EnvVariable.TEST_IMG_UTL.get(1), new Date(), "baba"));
        momentList.add(new Moment("03", EnvVariable.TEST_IMG_UTL.get(2), new Date(), "caca"));
    }

}
