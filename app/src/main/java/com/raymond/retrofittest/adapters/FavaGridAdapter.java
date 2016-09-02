package com.raymond.retrofittest.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.ui.BaseActivity;
import com.raymond.retrofittest.ui.MomentEditDialog;
import com.raymond.retrofittest.ui.ViewImageActivity;
import com.raymond.retrofittest.utils.Utils;

import java.util.List;

/**
 * Created by raymond on 8/16/16.
 */
public class FavaGridAdapter extends RecyclerView.Adapter<FavaGridAdapter.GridViewHolder>{


    private static final String TAG = "FavaGridAdapter";

    private List<Moment> moments;
    private Context context;

    public FavaGridAdapter(List<Moment> uris, Context context){
        this.moments = uris;
        this.context = context;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fava_grid, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Utils.loadImage(holder.daypic, Uri.parse(moments.get(position).getPhotoURL()),context, 100);
        final Moment moment = moments.get(position);

        Log.d(TAG,"on bind "+ position);

        holder.daypic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d(TAG, "long click");

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Title");
                builder.setItems(new CharSequence[]
                                {"删除", "编辑", "分享"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        DatabaseManager.deleteMoment(moment);
                                        refill(moments,true);
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        MomentEditDialog momentEditDialog = new MomentEditDialog(context,moment);
                                        momentEditDialog.show();
                                        dialog.dismiss();
                                        break;
                                    case 2:
                                        dialog.cancel();
                                        break;
                                }

                            }
                        });
                builder.create().show();

                return false;
            }
        });


        holder.daypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewImageActivity.openWithMomentId(context, moment.getId(),moment.getPhotoURL(),moment.getDesc());
            }
        });

    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView daypic;

        public GridViewHolder(View itemView) {
            super(itemView);
            daypic = (ImageView) itemView.findViewById(R.id.fava_img);
        }
    }

    public void refill(List<Moment> data, boolean flush){
        if(flush) moments.clear();
        if(data!=null){
            moments.addAll(data);
        }
        notifyDataSetChanged();
    }
}
