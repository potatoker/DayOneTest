package com.raymond.retrofittest.ui.myoneday;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.ui.EditMomentActivity;
import com.raymond.retrofittest.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 16/3/12.
 */
public class DetailDayAdapter extends RecyclerView.Adapter<DetailDayAdapter.MomentViewHolder> {


    private List<Moment> moments;
    private Context context;
    private ImageLoader imageLoader;

    public DetailDayAdapter(List<Moment> moments, Context context) {
        super();
        this.moments = moments;
        this.context = context;
    }


    @Override
    public MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new MomentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MomentViewHolder holder, int position) {

        final Moment moment = moments.get(position);

        Utils.loadImage(holder.vImg, Uri.parse(moment.getPhotoURL()),context,context.getResources().getDimensionPixelSize(R.dimen.all_photo_size));

        holder.vTime.setText(moment.getDate().toString());
        holder.vLocation.setText(moment.getLocation());
        holder.vDesc.setText(moment.getDesc());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMomentActivity.class);
                intent.putExtra(EditMomentActivity.KEY_MOMENT_ID, moment.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MomentViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vImg;
        protected TextView vTime;
        protected TextView vLocation;
        protected TextView vDesc;
        protected CardView cardView;


        public MomentViewHolder(View itemView) {
            super(itemView);

            vImg = (ImageView) itemView.findViewById(R.id.card_img);
            vTime = (TextView) itemView.findViewById(R.id.card_time);
            vLocation = (TextView) itemView.findViewById(R.id.card_location);
            vDesc = (TextView) itemView.findViewById(R.id.card_desc);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }


    public void refill(List<Moment> data, boolean flush){
        if(flush)
            moments.clear();
        if(data != null){
            moments.addAll(data);
            notifyDataSetChanged();
        }
    }


}
