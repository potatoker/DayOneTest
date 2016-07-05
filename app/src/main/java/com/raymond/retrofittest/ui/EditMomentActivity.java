package com.raymond.retrofittest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

public class EditMomentActivity extends AppCompatActivity {


    private static final String TAG = "EditMomentActivity";
    public static final String KEY_MOMENT_ID = "key_moment_id";


    private int photoSize;

    private String momentId;

    @Bind(R.id.edit_pre_img)
    ImageView editImg;

    @Bind(R.id.edit_desc)
    EditText editDesc;


    @Bind(R.id.edit_save)
    Button saveButton;

    @Bind(R.id.edit_discard)
    Button discardButton;

    @Bind(R.id.edit_time)
    TextView editTime;

    @Bind(R.id.edit_location)
    TextView editLocation;


//    DatabaseManager dbm;

    private Moment moment;
    private Uri imgUri;
    private String desc;
    private String date;


    public static void openWithMomentId(Activity openingActivity, String momentId){
        Intent intent = new Intent(openingActivity, EditMomentActivity.class);
        intent.putExtra(KEY_MOMENT_ID, momentId);
        openingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_moment);

        photoSize = getResources().getDimensionPixelSize(R.dimen.all_photo_size);
        if(savedInstanceState == null){
            momentId = getIntent().getParcelableExtra(KEY_MOMENT_ID);
        }else{
            momentId = savedInstanceState.getParcelable(KEY_MOMENT_ID);
        }

        moment = DatabaseManager.getMomentById(momentId);
        initView();
    }


    public void initView(){
        if(moment != null){
            imgUri = Uri.parse(moment.getPhotoURL());

            Utils.loadImage(editImg, imgUri, EditMomentActivity.this, photoSize);
            editDesc.setText(moment.getDesc());
            editTime.setText(moment.getDate());
            editLocation.setText(moment.getLocation());
        }
    }


    @OnClick(R.id.edit_save)
    public void saveEdit(){
        if(moment != null) {
            moment.setDesc(editDesc.getText().toString());
        }
    }

    @OnClick(R.id.edit_discard)
    public void discardEdit(){
        new AlertDialog.Builder(EditMomentActivity.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        DatabaseManager.deleteMoment(momentId);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
