package com.raymond.retrofittest.ui.myoneday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.raymond.retrofittest.R;

public class MyDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_day);


        mydayList = (RecyclerView)findViewById(R.id.myday_list);


    }
    private RecyclerView mydayList;


}
