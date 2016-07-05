package com.raymond.retrofittest.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raymond.retrofittest.Message;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.datatype.OneDay;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.db.DatabaseHelper;
import com.raymond.retrofittest.db.DatabaseManager;
import com.raymond.retrofittest.utils.Utils;


import org.greenrobot.eventbus.EventBus;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by raymond on 6/16/16.
 */
public class DayAdapterWithFooter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = "DayAdapterWithFooter";

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;


    private List<Moment> moments;
    private Context context;
    private boolean flag=false;


    public DayAdapterWithFooter(List<Moment> moments, Context context){
        this.moments = moments;
        this.context = context;

        if(moments == null){
            this.moments = new ArrayList<>();
            flag = true;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            MomentViewHolder mvh = new MomentViewHolder(itemView);
            return mvh;
        }else if (viewType == TYPE_FOOTER){
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(footerView);
            return footerViewHolder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MomentViewHolder){
            Moment moment = moments.get(position);

            MomentViewHolder itemHolder = (MomentViewHolder) holder;
            Utils.loadImage(itemHolder.momentImg, Uri.parse(moment.getPhotoURL()), context);
            itemHolder.location.setText(moment.getLocation());
            itemHolder.desc.setText(moment.getDesc());
            itemHolder.time.setText(moment.getDate());
        }
        else if(holder instanceof FooterViewHolder){

            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            footerViewHolder.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(moments.size()<2){
                        Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d(TAG, "commit");
                        commitCurrentDay();
                    }
                }
            });



        }

    }



    @Override
    public int getItemCount() {
        return moments.size()+1;
    }

    public final static class MomentViewHolder extends RecyclerView.ViewHolder {
        ImageView momentImg;
        TextView time;
        TextView location;
        TextView desc;

        public MomentViewHolder(View itemView) {
            super(itemView);
            momentImg = (ImageView) itemView.findViewById(R.id.card_img);
            time = (TextView) itemView.findViewById(R.id.card_time);
            location = (TextView) itemView.findViewById(R.id.card_location);
            desc = (TextView) itemView.findViewById(R.id.card_desc);
        }
    }

    public final static class FooterViewHolder extends RecyclerView.ViewHolder{
        Button saveButton;
//        Button addButton;

        public FooterViewHolder(View footerView){
            super(footerView);
            saveButton = (Button) footerView.findViewById(R.id.current_done);
//            addButton = (Button) footerView.findViewById(R.id.current_add);
        }
    }


    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == moments.size ();
    }

    public void refill(List<Moment> data, boolean flush){
        if(flush) moments.clear();
        if(data != null){
            moments.addAll(data);
        }else{
            flag = true;
        }

        notifyDataSetChanged();
    }

    private void addCurrentDay(){

        OneDay day = new OneDay();
        day.setTime(Utils.getCurrentDate());
        day.setUserId(User.getInstance().getuId());
        day.setFlag(DatabaseHelper.FLAG_DAY_CURRENT);
        day.setFlag(DatabaseHelper.FLAG_DAY_NO_COMMIT);
        DatabaseManager.addDay(day);

        EventBus.getDefault().post(new Message());

    }

//    @Subscribe
//    public void changeFlag(Message message){
//        flag = false;
//        notifyDataSetChanged();
//    }


    public void commitCurrentDay(){
        OneDay currentDay;
        currentDay = DatabaseManager.getCurrentDay();

        if(currentDay == null){
            Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();

        }else{
            currentDay.setFlag(DatabaseHelper.FLAG_DAY__COMMIT);
            DatabaseManager.addDay(currentDay);
            refill(null, true);
        }
    }
}
