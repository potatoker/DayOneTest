package com.raymond.retrofittest.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.utils.Utils;

import java.util.List;

/**
 * Created by raymond on 8/15/16.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder>{

    private static final String TAG = "GridAdapter";

    private List<Moment> moments;
    private Context context;

    public GridAdapter(List<Moment> uris, Context context){
        this.moments = uris;
        this.context = context;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grib, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Utils.loadImage(holder.daypic, Uri.parse(moments.get(position).getPhotoURL()),context, 100);
    }

    @Override
    public int getItemCount() {
        return moments.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView daypic;

        public GridViewHolder(View itemView) {
            super(itemView);
            daypic = (ImageView) itemView.findViewById(R.id.grid_img);
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
