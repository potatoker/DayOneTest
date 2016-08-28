package com.raymond.retrofittest.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.utils.Utils;

/**
 * Created by raymond on 8/16/16.
 */
public class MomentEditDialog extends Dialog implements android.view.View.OnClickListener{


    Context context;
    ToggleButton save;
    ToggleButton cancel;
    EditText editTextDesc;
    EditText editTextLoc;

    ImageView imageView;
    Moment moment;
    public MomentEditDialog(Context context, Moment moment) {
        super(context);
        this.context = context;
        this.moment = moment;
    }

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);


        setContentView(R.layout.dailog_moment_edit);

        setTitle("编辑Moment");

        final TextView textView = (TextView)  findViewById(android.R.id.title);
        if(textView != null) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }


        imageView = (ImageView)findViewById(R.id.dialogEdit_img);


        save = (ToggleButton)findViewById(R.id.dialog_save_to_current);
        cancel = (ToggleButton)findViewById(R.id.dialog_save_to_fav);
        editTextDesc = (EditText)findViewById(R.id.dialogEdit_desc);
        editTextLoc = (EditText)findViewById(R.id.dialogEdit_position);

        if(moment!=null){
            editTextDesc.setText(moment.getDesc());
            editTextLoc.setText(moment.getLocation());
        }

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);

        Utils.loadImage(imageView, Uri.parse(moment.getPhotoURL()), context,300);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_save_to_current:
                setMoment();
                dismiss();
                break;
            case R.id.dialog_save_to_fav:
                dismiss();
                break;
        }
    }


    public void setMoment(){
        moment.setDesc(editTextDesc.getText().toString());
        moment.setLocation(editTextLoc.getText().toString());
        DatabaseManager.addMoment(moment);
    }
}
