package com.raymond.retrofittest.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.raymond.retrofittest.R;

/**
 * Created by raymond on 8/16/16.
 */
public class MoreDialog extends Dialog {

    ListView listView;
    private Context context;

    public String[] selections = {"删除","分享","编辑"};

    public MoreDialog(Context context){
        super(context);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);

        listView = (ListView)findViewById(R.id.dlg_priority_lvw);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, selections);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:

                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });

    }
}
