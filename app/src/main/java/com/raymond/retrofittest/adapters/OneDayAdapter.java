package com.raymond.retrofittest.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.tools.VolleySingleton;
import com.raymond.retrofittest.utils.Utils;

import java.util.List;

/**
 * Created by raymond on 16/4/6.
 */
public class OneDayAdapter extends RecyclerView.Adapter<OneDayAdapter.MomentViewHolder>{

    private static final String TAG = "DetailDayAdapter";
    private List<Moment> moments;
    private Context context;

    public OneDayAdapter(List<Moment> moments, Context context){
        this.moments = moments;
        this.context = context;
    }

    @Override
    public MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        MomentViewHolder mvh = new MomentViewHolder(itemView);
        return mvh;
    }

//    @Override
//    public void onBindViewHolder(MomentViewHolder holder, int position) {
//
//        Moment moment = moments.get(position);
//
//        holder.time.setText(moment.getDate().toString());
//        holder.location.setText(moment.getLocation());
//        holder.desc.setText(moment.getDesc());
//
//        imageLoader = VolleySingleton.newInstance().getImageLoader();
//        holder.momentImg.setDefaultImageResId(R.drawable.loading);
//        holder.momentImg.setErrorImageResId(R.drawable.fill);
//        holder.momentImg.setImageUrl(moment.getPhotoURL(), imageLoader);
//
//    }

    public void onBindViewHolder(MomentViewHolder holder, int position){

        Moment moment = moments.get(position);

        Utils.loadImage(holder.momentImg, Uri.parse(moment.getPhotoURL()), context);
        holder.location.setText(moment.getLocation());
        holder.desc.setText(moment.getDesc());
        holder.time.setText(moment.getDate());

    }

    @Override
    public int getItemCount() {
        return moments.size();
    }




    public final static class MomentViewHolder extends RecyclerView.ViewHolder{
        ImageView momentImg;
        TextView time;
        TextView location;
        TextView desc;

        public MomentViewHolder(View itemView){
            super(itemView);
            momentImg = (ImageView)itemView.findViewById(R.id.card_img);
            time = (TextView)itemView.findViewById(R.id.card_time);
            location = (TextView)itemView.findViewById(R.id.card_location);
            desc = (TextView)itemView.findViewById(R.id.card_desc);
        }
    }

    public void refill(List<Moment> data, boolean flush){
        if(flush) moments.clear();
        if(data != null){
            moments.addAll(data);
            notifyDataSetChanged();
        }
    }



}
