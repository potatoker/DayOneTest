package com.raymond.retrofittest.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
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

import java.util.List;

/**
 * Created by raymond on 16/4/6.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MomentViewHolder>{

    private static final String TAG = "RVAdapter";
    private List<Moment> moments;
    private ImageLoader imageLoader;

    public RVAdapter(List<Moment> moments){
        this.moments = moments;
    }

    @Override
    public MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        MomentViewHolder mvh = new MomentViewHolder(itemView);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MomentViewHolder holder, int position) {

        Moment moment = moments.get(position);

        holder.time.setText(moment.getDate().toString());
        holder.location.setText(moment.getLocation());
        holder.desc.setText(moment.getDesc());

        imageLoader = VolleySingleton.getInstance().getImageLoader();
        holder.momentImg.setDefaultImageResId(R.drawable.loading);
        holder.momentImg.setErrorImageResId(R.drawable.fill);
        holder.momentImg.setImageUrl(moment.getPhotoURL(), imageLoader);


    }

    @Override
    public int getItemCount() {
        return moments.size();
    }



    public final static class MomentViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView momentImg;
        TextView time;
        TextView location;
        TextView desc;

        public MomentViewHolder(View itemView){
            super(itemView);
            momentImg = (NetworkImageView)itemView.findViewById(R.id.card_img);
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
