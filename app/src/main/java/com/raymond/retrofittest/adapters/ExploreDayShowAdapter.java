package com.raymond.retrofittest.adapters;


import android.content.Context;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.AsyncTask;
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
import com.raymond.retrofittest.ui.ViewImageActivity;
import com.raymond.retrofittest.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by raymond on 8/30/16.
 */
public class ExploreDayShowAdapter extends RecyclerView.Adapter<ExploreDayShowAdapter.MomentViewHolder>{

    private static final String TAG = "ExploreDayShowAdapter";
    List<Moment> moments;
    Context context;

    public ExploreDayShowAdapter(List<Moment> moments, Context context){
        this.moments = moments;
        this.context = context;
    }

    @Override
    public MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        MomentViewHolder mvh = new MomentViewHolder(itemView);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MomentViewHolder holder, int position) {
        final Moment moment = moments.get(position);
        MomentViewHolder itemHolder = (MomentViewHolder) holder;
        Utils.loadImage(itemHolder.momentImg, Uri.parse(moment.getRemote_url()), context);
//        loadBitmap(moments.get(position),holder.momentImg);
        itemHolder.location.setText(moment.getLocation());
        itemHolder.desc.setText(moment.getDesc());
        itemHolder.time.setText(moment.getDate());

        itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked @!!!!!!!!!!!!!");
                ViewImageActivity.openWithMomentId(context,moment.getId(), moment.getRemote_url(), moment.getDesc());
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


    public static boolean cancelPotentialWork(Moment moment, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final Moment bitmapData = bitmapWorkerTask.moment;
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == null || !bitmapData.getId().equals(moment.getId())) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public void loadBitmap(Moment moment, ImageView imageView) {
        if (cancelPotentialWork(moment, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(context.getResources(),task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(moment);
        }
    }

    public static class BitmapWorkerTask extends AsyncTask<Moment, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        public Moment moment;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Moment... params) {
            moment = params[0];
            return Utils.getBitMapFromString(moment.getImgString());
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }


}
