package com.raymond.retrofittest.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.ui.MomentEditDialog;
import com.raymond.retrofittest.ui.ViewImageActivity;
import com.raymond.retrofittest.utils.Utils;

import java.util.List;

/**
 * Created by raymond on 8/18/16.
 */
public class DayShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DayShowAdapter";

    private List<Moment> moments;
    private Context context;

    public DayShowAdapter(List<Moment> moments, Context context){
        this.moments = moments;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        MomentViewHolder mvh = new MomentViewHolder(itemView);
        return mvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Moment moment = moments.get(position);
        MomentViewHolder itemHolder = (MomentViewHolder) holder;
        Utils.loadImage(itemHolder.momentImg, Uri.parse(moment.getPhotoURL()), context);
        itemHolder.location.setText(moment.getLocation());
        itemHolder.desc.setText(moment.getDesc());
        itemHolder.time.setText(moment.getDate());

        itemHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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

        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked @!!!!!!!!!!!!!");
                ViewImageActivity.openWithMomentId(context,moment.getId(), moment.getPhotoURL(), moment.getDesc());
            }
        });

    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    public final static class MomentViewHolder extends RecyclerView.ViewHolder {
        ImageView momentImg;
        TextView time;
        TextView location;
        TextView desc;
        CardView cardView;

        public MomentViewHolder(View itemView) {
            super(itemView);
            momentImg = (ImageView) itemView.findViewById(R.id.card_img);
            time = (TextView) itemView.findViewById(R.id.card_time);
            location = (TextView) itemView.findViewById(R.id.card_location);
            desc = (TextView) itemView.findViewById(R.id.card_desc);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public void refill(List<Moment> data, boolean flush){
        if(moments!=null) {
            if (flush) moments.clear();
        }
        if(data != null) {
            moments.addAll(data);
        }

        notifyDataSetChanged();
    }




}
