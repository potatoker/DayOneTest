package com.raymond.retrofittest.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.GridAdapter;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.db.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DayPublish extends AppCompatActivity {

    @Bind(R.id.dayPublish_recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.pdays_save_to_current)
    ToggleButton save;

    @Bind(R.id.pdays_save_to_fav)
    ToggleButton remove;

    @Bind(R.id.pdays_title)
    EditText title;

    @Bind(R.id.pdays_desc)
    EditText desc;

    GridAdapter gridAdapter;
    OneDay currentDay;
    List<Moment> moments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_publish);
        ButterKnife.bind(this);
        moments = new ArrayList<>();
        currentDay = DatabaseManager.getCurrentDayWithMoments();
        initRecyclerView();
        initSave();
        initRemove();
    }


    public void initRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        gridAdapter = new GridAdapter(moments,DayPublish.this);
        recyclerView.setAdapter(gridAdapter);
        getCurrentMoments();

    }

    public void getCurrentMoments(){
        if(currentDay!=null){
            List<Moment> moments = currentDay.getMoments();
            gridAdapter.refill(moments, true);
        }
    }


    public void initSave(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitCurrentDay();
                DayPublish.this.finish();
            }
        });
    }

    public void initRemove(){
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DayPublish.this);
                builder.setTitle("Title");
                builder.setItems(new CharSequence[]
                                {"删除一天", "全部收藏", "返回"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        deleteCurrentDay();
                                        dialog.dismiss();
                                        DayPublish.this.finish();
                                        break;
                                    case 1:

                                        dialog.dismiss();
                                        DayPublish.this.finish();
                                        break;
                                    case 2:
                                        dialog.cancel();
                                        break;
                                }

                            }
                        });
                builder.create().show();

            }
        });
    }


    public void deleteCurrentDay(){
    }

    public void moveAllCurrentDayToFavs(){
    }

    public void commitCurrentDay(){
        if(currentDay == null){
            Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show();

        }else{
            currentDay.setFlag(DatabaseHelper.FLAG_DAY__COMMIT);
            currentDay.setDayTitle(title.getText().toString());
            currentDay.setDesc(desc.getText().toString());
            DatabaseManager.addDay(currentDay);

        }
    }
}
