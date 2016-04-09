package com.raymond.retrofittest.ui.myoneday;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.tools.BitmapCache;
import com.raymond.retrofittest.tools.CustomVolleyRequest;

import java.util.ArrayList;

/**
 * Created by raymond on 16/3/12.
 */
public class OneDayAdapter extends RecyclerView.Adapter<OneDayAdapter.MomentViewHolder> {


    private ArrayList<Moment> moments;
    private Context context;
    private ImageLoader imageLoader;

    public OneDayAdapter(ArrayList<Moment> moments, Context context) {
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

        Moment moment = moments.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader
                .getImageListener(holder.vImg, R.drawable.loading, R.drawable.fill);
        imageLoader.get(moment.getPhotoURL(),listener);

        holder.vTime.setText(moment.getDate().toString());
        holder.vLocation.setText(moment.getLocation());
        holder.vDesc.setText(moment.getDesc());

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


        public MomentViewHolder(View itemView) {
            super(itemView);

            vImg = (ImageView) itemView.findViewById(R.id.card_img);
            vTime = (TextView) itemView.findViewById(R.id.card_time);
            vLocation = (TextView) itemView.findViewById(R.id.card_location);
            vDesc = (TextView) itemView.findViewById(R.id.card_desc);
        }
    }

}
