package com.raymond.retrofittest.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.DayShowAdapter;
import com.raymond.retrofittest.adapters.ExploreDayShowAdapter;
import com.raymond.retrofittest.datatype.Moment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExploreDayShowActivity extends AppCompatActivity {
    private static final String TAG = "ExploreDayShowActivity";
    public static final String KEY_MOMENTS = "key_moments";

    @Bind(R.id.showDay_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.dayShow_refresher)
    SwipeRefreshLayout swipeRefreshLayout;

    ExploreDayShowAdapter adapter;

    private List<Moment> moments = new ArrayList<>();

    public static void openWithMoments(Context openingActivity, ArrayList<Moment> moments){
        Intent intent = new Intent(openingActivity, ExploreDayShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_MOMENTS,moments);
        intent.putExtras(bundle);
        openingActivity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_show);
        ButterKnife.bind(this);


        if(savedInstanceState==null){

            Bundle bundle = getIntent().getExtras();
            moments = bundle.getParcelableArrayList(KEY_MOMENTS);
        }else{

            Bundle bundle = getIntent().getExtras();
            moments = savedInstanceState.getParcelableArrayList(KEY_MOMENTS);
        }

        initRecyclerView();
    }

    public void initRecyclerView(){
        adapter = new ExploreDayShowAdapter(moments,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
