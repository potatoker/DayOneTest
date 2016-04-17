package com.raymond.retrofittest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.EnvVariable;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.OneDayAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.net.DayInfoLoader;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DayActivity extends AppCompatActivity implements DataPresenter.GetOneDayInterface{

    @Bind(R.id.recycler_view)
    RecyclerView rv;

    private OneDayAdapter oneDayAdapter;
    private List<Moment> momentList = new ArrayList<>();//不至于是null以生成adapter，后面会在网络中更新

    private String dayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        dayId = intent.getStringExtra(EnvVariable.DAY_ID);

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        initializeData();
        oneDayAdapter = new OneDayAdapter(momentList);
        rv.setAdapter(oneDayAdapter);

        DataPresenter.requestOneDay(User.getInstance().getuId(), dayId, DayActivity.this);

    }

    private void initializeData(){
        momentList = new ArrayList<>();
        momentList.add(new Moment("01", EnvVariable.TEST_IMG_UTL.get(0), new Date(), "lala"));
        momentList.add(new Moment("02", EnvVariable.TEST_IMG_UTL.get(1), new Date(), "baba"));
        momentList.add(new Moment("03", EnvVariable.TEST_IMG_UTL.get(2), new Date(), "caca"));
    }

    @Override
    public void onGetOneDay(OneDay day, Boolean ok) {
        if(ok){
            oneDayAdapter.refill(day.getMoments(), true);
        }else{
            Toast.makeText(DayActivity.this, R.string.error_network, Toast.LENGTH_SHORT).show();
        }
    }
}
