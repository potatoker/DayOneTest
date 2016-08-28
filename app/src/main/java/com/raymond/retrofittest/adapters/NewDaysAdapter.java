package com.raymond.retrofittest.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.ui.DayShowActivity;
import com.raymond.retrofittest.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 7/30/16.
 */
public class NewDaysAdapter extends RecyclerView.Adapter<NewDaysAdapter.OneDayViewHolder>{

    private static final String TAG = "DaysAdapter";
    private List<OneDay> days;
    private Context context;

    public NewDaysAdapter(List<OneDay> days, Context context){

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



    @Override
    public OneDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_card, parent, false);
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
        Utils.loadImage(holder.big, Uri.parse(moments.get(0).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.big_photo_size));
        Utils.loadImage(holder.ban1, Uri.parse(moments.get(1).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.small_photo_size));
        Utils.loadImage(holder.ban2, Uri.parse(moments.get(2).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.small_photo_size));

        holder.titleView.setText(oneDay.getDayTitle());
        String title = oneDay.getDesc();
        if(title !=null && title.length()>20)
            title = title.substring(0, 20);

        holder.desc.setText(title);

        holder.time.setText(oneDay.getTime());

        if(oneDay.getAccess() == DatabaseHelper.ACCESS_OPEN){
            holder.openButton.setBackgroundResource(R.drawable.ic_lock_open_black_48dp);
//            Log.d(TAG, "in open num:" +position + DatabaseManager.getOpenDays().size());


        }else if(oneDay.getAccess() == DatabaseHelper.ACCESS_PRIVET){
            holder.openButton.setBackgroundResource(R.drawable.ic_lock_outline_black_48dp);
//            Log.d(TAG, "in private num:" + position +  DatabaseManager.getOpenDays().size());
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayShowActivity.openWithDayId(context, oneDay.getDayId());
            }
        });


        holder.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "position "+position+" clicked with ori access:"+oneDay.getAccess());

                if(oneDay.getAccess()==DatabaseHelper.ACCESS_OPEN){
                    oneDay.setAccess(DatabaseHelper.ACCESS_PRIVET);
                    DatabaseManager.addDay(oneDay);
//                    Log.d(TAG, ""+oneDay.getAccess());
                    holder.openButton.setBackgroundResource(R.drawable.ic_lock_outline_black_48dp);
//                    Log.d(TAG, "open num:" + DatabaseManager.getOpenDays().size());
                }else{
                    oneDay.setAccess(DatabaseHelper.ACCESS_OPEN);
                    DatabaseManager.addDay(oneDay);

//                    Log.d(TAG, ""+oneDay.getAccess());
                    holder.openButton.setBackgroundResource(R.drawable.ic_lock_open_black_48dp);
//                    Log.d(TAG, "open num:" + DatabaseManager.getOpenDays().size());

                }
            }
        });

        holder.moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Title");
                builder.setItems(new CharSequence[]
                                {"删除", "分享", "编辑"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                switch (which) {
                                    case 0:
                                        days.remove(position);
                                        refill(days,true);
                                        deleteDay(oneDay);
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        dialog.dismiss();
                                        break;
                                    case 2:
                                        dialog.cancel();
                                        break;
                                }

                            }
                        });
                builder.create().show();

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


        TextView titleView;
        TextView desc;

        TextView time;
        ImageButton openButton;
        ImageView moreButton;
        CardView cardView;

        public OneDayViewHolder(View itemView){
            super(itemView);
            big = (ImageView)itemView.findViewById(R.id.day_img);
            ban1 = (ImageView) itemView.findViewById(R.id.img1);
            ban2 = (ImageView) itemView.findViewById(R.id.img2);

            titleView = (TextView) itemView.findViewById(R.id.day_title);
            openButton = (ImageButton) itemView.findViewById(R.id.access_public);
            desc = (TextView) itemView.findViewById(R.id.day_desc);
            time = (TextView) itemView.findViewById(R.id.newcard_time);
            moreButton = (ImageView) itemView.findViewById(R.id.day_more);
            cardView = (CardView) itemView.findViewById(R.id.myDays_cardView);
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


    public void deleteDay(OneDay oneDay){

        DatabaseManager.deleteDay(oneDay);
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