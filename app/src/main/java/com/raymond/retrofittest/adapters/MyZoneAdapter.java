package com.raymond.retrofittest.adapters;

import android.content.Context;
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
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.ui.DayShowActivity;
import com.raymond.retrofittest.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymond on 8/16/16.
 */
public class MyZoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MyZoneAdapter";

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM =1;

    private List<OneDay> days;
    private Context context;

    private User user;

    public MyZoneAdapter(List<OneDay> days, User user, Context context){
        this.days = new ArrayList<>();
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_card, parent, false);
            MyMomentViewHolder mvh = new MyMomentViewHolder(itemView);
            return mvh;
        }else if(viewType == TYPE_HEADER){
            View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_myzone, parent, false);
            HeaderViewHeader headerViewHeader = new HeaderViewHeader(headView);
            return headerViewHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder2, int position) {

        if(holder2 instanceof MyMomentViewHolder){
            final OneDay oneDay = days.get(position-1);

            MyMomentViewHolder holder = (MyMomentViewHolder) holder2;
            ArrayList<Moment> moments = oneDay.getMoments();


            if(holder.big == null){
                Log.d(TAG, "big is null");
            }

            Utils.loadImage(holder.big, Uri.parse(moments.get(0).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.big_photo_size));
            Utils.loadImage(holder.ban1, Uri.parse(moments.get(1).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.small_photo_size));
            Utils.loadImage(holder.ban2, Uri.parse(moments.get(2).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.small_photo_size));

            holder.titleView.setText(oneDay.getDayTitle());
            String title = oneDay.getDesc();
            if(title !=null && title.length()>20)
                title = title.substring(0, 20);

            holder.desc.setText(title);

            holder.time.setText(oneDay.getTime());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DayShowActivity.openWithDayId(context, oneDay.getDayId());
                }
            });

        }else if(holder2 instanceof HeaderViewHeader){
            HeaderViewHeader headerViewHeader = (HeaderViewHeader)holder2;
            if(user !=null) {

                if(user.getAvataruri()!=null){
                    Utils.loadImage(headerViewHeader.profileImg,
                            Uri.parse(user.getAvataruri()), context, 200);
                }else{
                    Utils.loadImageFromNet(headerViewHeader.profileImg,
                            user.getAvatarurl(), context, 200);

                    Utils.imageDownload(context, user.getAvatarurl());
                }

//                headerViewHeader.name.setText(user.getName());

                headerViewHeader.name.setText(User.getInstance().getName());
//                headerViewHeader.desc.setText(user.getDesc());
                headerViewHeader.desc.setText(User.getInstance().getUid());
//                headerViewHeader.email.setText(user.getEmail());
                headerViewHeader.email.setText(User.getInstance().getEmail());
                headerViewHeader.motto.setText(user.getMotto());
            }else {
                headerViewHeader.name.setText(User.getInstance().getName());
                headerViewHeader.desc.setText(User.getInstance().getUid());

                headerViewHeader.email.setText(User.getInstance().getEmail());
            }



        }



    }

    @Override
    public int getItemCount() {
        return days.size()+1;
    }

    public final static class MyMomentViewHolder extends RecyclerView.ViewHolder{
        ImageView big;
        ImageView ban1;
        ImageView ban2;


        TextView titleView;
        TextView desc;

        TextView time;
        ImageButton openButton;
        ImageView moreButton;

        CardView cardView;

        public MyMomentViewHolder(View itemView){
            super(itemView);
            big = (ImageView)itemView.findViewById(R.id.day_img);

            if(big == null){
                Log.d(TAG, "big is null??????????????");
            }

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


    public final static class HeaderViewHeader extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        TextView email;
        TextView motto;
        ImageView profileImg;

        public HeaderViewHeader(View headerView){
            super(headerView);
            name = (TextView)headerView.findViewById(R.id.header_name);
            desc = (TextView)headerView.findViewById(R.id.header_desc);
            email = (TextView) headerView.findViewById(R.id.header_email);
            motto = (TextView) headerView.findViewById(R.id.header_motto);
            profileImg = (ImageView) headerView.findViewById(R.id.img_profile_pic);
        }
    }

    public int getItemViewType (int position) {
        if(position==0) {
            return TYPE_HEADER;
        }else
            return TYPE_ITEM;
    }


    public void saveProfileImg(){

    }

    public void refill(List<OneDay> data, boolean flush){

        if(flush) days.clear();
        if(data != null){

            days.addAll(data);
        }
        notifyDataSetChanged();
    }


}
