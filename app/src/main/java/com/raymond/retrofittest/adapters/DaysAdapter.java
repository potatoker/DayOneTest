package com.raymond.retrofittest.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.tools.VolleySingleton;
import com.raymond.retrofittest.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 16/5/22.
 */
public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.OneDayViewHolder>{

    private static final String TAG = "DaysAdapter";
    private List<OneDay> days;
    private Context context;

    public DaysAdapter(List<OneDay> days, Context context){

        Log.d(TAG,"dayAdapter construction");
        this.days = days;
        if(days == null){

//            Log.d(TAG, "days is null");
            this.days = new ArrayList<>();
        }else{
            Log.d(TAG, ""+ this.days.size());
        }

        this.context = context;
//        imageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    public DaysAdapter(){}

    @Override
    public OneDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_days_card, parent, false);
        OneDayViewHolder dvh = new OneDayViewHolder(view);
        return dvh;
    }

    @Override
    public void onBindViewHolder(final OneDayViewHolder holder, final int position) {

        final OneDay oneDay = days.get(position);

//        holder.big.setImageUrl(oneDay.getMoments().get(0).getPhotoURL(), imageLoader);
//        holder.ban1.setImageUrl(oneDay.getMoments().get(1).getPhotoURL(), imageLoader);
//        holder.ban2.setImageUrl(oneDay.getMoments().get(2).getPhotoURL(), imageLoader);
//        holder.ban3.setImageUrl(oneDay.getMoments().get(3).getPhotoURL(), imageLoader);
//        setDefaultImage(holder);

        ArrayList<Moment> moments = oneDay.getMoments();
        Utils.loadImage(holder.big, Uri.parse(moments.get(0).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.all_photo_size));
        Utils.loadImage(holder.ban1, Uri.parse(moments.get(1).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.thumb_photo_size));
        Utils.loadImage(holder.ban2, Uri.parse(moments.get(2).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.thumb_photo_size));
//        Utils.loadImage(holder.ban3, Uri.parse(moments.get(3).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.thumb_photo_size));
        holder.titleView.setText(oneDay.getDayTitle());

        holder.desc.setText("sadfasdfaszdfadf"+oneDay.getDesc());

//        if(oneDay.getAccess() == DatabaseHelper.ACCESS_OPEN){
//            holder.openButton.setChecked(true);
//        }else{
//            holder.openButton.setChecked(false);
//        }

        Log.d(TAG, "in position " +  position + " access is: " + oneDay.getAccess());

        if(oneDay.getAccess() == DatabaseHelper.ACCESS_OPEN){
            holder.openButton.setBackgroundResource(R.drawable.ic_public_blue_48dp);
//            Log.d(TAG, "in open num:" +position + DatabaseManager.getOpenDays().size());


        }else if(oneDay.getAccess() == DatabaseHelper.ACCESS_PRIVET){
            holder.openButton.setBackgroundResource(R.drawable.ic_public_grey_48dp);
//            Log.d(TAG, "in private num:" + position +  DatabaseManager.getOpenDays().size());

        }

        holder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "position "+position+" clicked with ori access:"+oneDay.getAccess());

                if(oneDay.getAccess()==DatabaseHelper.ACCESS_OPEN){
                    oneDay.setAccess(DatabaseHelper.ACCESS_PRIVET);
                    DatabaseManager.addDay(oneDay);
//                    Log.d(TAG, ""+oneDay.getAccess());
                    holder.openButton.setBackgroundResource(R.drawable.ic_public_grey_48dp);
//                    Log.d(TAG, "open num:" + DatabaseManager.getOpenDays().size());
                }else{
                    oneDay.setAccess(DatabaseHelper.ACCESS_OPEN);
                    DatabaseManager.addDay(oneDay);

//                    Log.d(TAG, ""+oneDay.getAccess());
                    holder.openButton.setBackgroundResource(R.drawable.ic_public_blue_48dp);
//                    Log.d(TAG, "open num:" + DatabaseManager.getOpenDays().size());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    public final static class OneDayViewHolder extends RecyclerView.ViewHolder{
        ImageView big;
        ImageView ban1;
        ImageView ban2;
        ImageView ban3;

        TextView titleView;
        TextView desc;

        ImageButton openButton;

        public OneDayViewHolder(View itemView){
            super(itemView);
            big = (ImageView)itemView.findViewById(R.id.day_img);
            ban1 = (ImageView) itemView.findViewById(R.id.img1);
            ban2 = (ImageView) itemView.findViewById(R.id.img2);
            ban3 = (ImageView) itemView.findViewById(R.id.img3);
            titleView = (TextView) itemView.findViewById(R.id.day_title);
            openButton = (ImageButton) itemView.findViewById(R.id.access_public);
            desc = (TextView) itemView.findViewById(R.id.day_desc);
        }
    }

    public void refill(List<OneDay> data, boolean flush){

        Log.d(TAG,"I'm here");
        if(flush) days.clear();
        if(data != null){
            Log.d(TAG, "got here!!!!");
            days.addAll(data);
        }
        notifyDataSetChanged();
    }

//    private void setDefaultImage(OneDayViewHolder holder){
//        holder.big.setDefaultImageResId(R.drawable.cat);
//        holder.ban1.setDefaultImageResId(R.drawable.cat);
//        holder.ban2.setDefaultImageResId(R.drawable.cat);
//        holder.ban3.setDefaultImageResId(R.drawable.cat);
//
//        holder.big.setErrorImageResId(R.drawable.errorcat);
//        holder.ban1.setErrorImageResId(R.drawable.errorcat);
//        holder.ban2.setErrorImageResId(R.drawable.errorcat);
//        holder.ban3.setErrorImageResId(R.drawable.errorcat);
//
//    }
}
