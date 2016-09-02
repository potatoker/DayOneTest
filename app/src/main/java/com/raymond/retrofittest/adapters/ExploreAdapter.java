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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Comment;
import com.raymond.retrofittest.datatype.ExploreDay;
import com.raymond.retrofittest.datatype.Moment;
import com.raymond.retrofittest.ui.CommentsActivity;
import com.raymond.retrofittest.ui.ExploreDayShowActivity;
import com.raymond.retrofittest.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by raymond on 8/29/16.
 */
public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {


    private static final String TAG = "ExploreAdapter";

    private List<ExploreDay> days;
    private Context context;

    public ExploreAdapter(List<ExploreDay> days, Context context){
        this.days = days;
        this.context = context;
    }

    @Override
    public ExploreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore,parent,false);
        ExploreViewHolder vh = new ExploreViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(ExploreViewHolder holder, int position) {
        final ExploreDay oneDay = days.get(position);
        ArrayList<Moment> moments = oneDay.getMoments();

        Utils.loadImage(holder.profileImg, Uri.parse(oneDay.getProfile_url()),context, 50);
//        Utils.loadImage(holder.big, Uri.parse(moments.get(0).getPhotoURL()),context, context.getResources().getDimensionPixelSize(R.dimen.big_photo_size));
//        Utils.loadImage(holder.img1, Uri.parse(moments.get(1).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.small_photo_size));
//        Utils.loadImage(holder.img2, Uri.parse(moments.get(2).getPhotoURL()), context, context.getResources().getDimensionPixelSize(R.dimen.small_photo_size));

        loadBitmap(moments.get(0),holder.big);
        loadBitmap(moments.get(1),holder.img1);
        loadBitmap(moments.get(2),holder.img2);

        holder.exploreName.setText(oneDay.getUserName());
        holder.dayDesc.setText(oneDay.getDesc());

        ArrayList<Comment> comments = oneDay.getComments();
        if(comments!=null){

            if(comments.size()>=2){
                holder.linearLayout1.setVisibility(View.VISIBLE);
                holder.linearLayout2.setVisibility(View.VISIBLE);
                holder.comment_user1.setText(comments.get(0).getUserName());
                holder.comment1.setText(comments.get(0).getComment());
                holder.comment_user2.setText(comments.get(1).getUserName());
                holder.comment2.setText(comments.get(1).getComment());
            }else if(comments.size()==1){
                holder.linearLayout1.setVisibility(View.VISIBLE);
                holder.comment_user1.setText(comments.get(0).getUserName());
                holder.comment1.setText(comments.get(0).getComment());
                holder.linearLayout2.setVisibility(View.GONE);
            }else if(comments.size()==0){
                holder.linearLayout1.setVisibility(View.GONE);
                holder.linearLayout2.setVisibility(View.GONE);
            }
        }else{
            holder.linearLayout1.setVisibility(View.GONE);
            holder.linearLayout2.setVisibility(View.GONE);
        }


        /**稍后添加关于点赞的代码**/

//        if(oneDay.getThumup()==1){
//            holder.thumbup.setChecked(true);
//        }else{
//            holder.thumbup.setChecked(false);
//        }

        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentsActivity.openWithDayId(context, oneDay.getDayId(),oneDay.getComments());
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreDayShowActivity.openWithMoments(context, oneDay.getMoments());

            }
        });

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public final static class ExploreViewHolder2 extends RecyclerView.ViewHolder{

        CircleImageView profileImg;
        TextView exploreName;

        ImageView big;
        ImageView img1;
        ImageView img2;

        TextView dayDesc;

        TextView comment_user1;
        TextView comment_user2;

        TextView comment1;
        TextView comment2;
        CardView cardView;


        public ExploreViewHolder2(View itemView) {
            super(itemView);

            profileImg = (CircleImageView)itemView.findViewById(R.id.item_explore_profile_image);
            exploreName = (TextView) itemView.findViewById(R.id.item_explore_name);
            big = (ImageView) itemView.findViewById(R.id.item_explore_day_img);
            img1 = (ImageView) itemView.findViewById(R.id.item_explore_img1);
            img2 = (ImageView) itemView.findViewById(R.id.item_explore_img2);
            dayDesc = (TextView)itemView.findViewById(R.id.item_explore_day_desc);
            comment_user1 = (TextView)itemView.findViewById(R.id.item_explore_comment_user1);
            comment_user2 =(TextView)itemView.findViewById(R.id.item_explore_comment_user2);
            comment1 = (TextView) itemView.findViewById(R.id.item_explore_comment1);
            comment2 = (TextView) itemView.findViewById(R.id.item_explore_comment2);
            cardView =(CardView)itemView.findViewById(R.id.item_explore_cardView);
        }
    }

    public final static class ExploreViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.item_explore_profile_image)
        CircleImageView profileImg;

        @Bind(R.id.item_explore_name)
        TextView exploreName;

        @Bind(R.id.item_explore_day_img)
        ImageView big;

        @Bind(R.id.item_explore_img1)
        ImageView img1;

        @Bind(R.id.item_explore_img2)
        ImageView img2;

        @Bind(R.id.item_explore_day_desc)
        TextView dayDesc;

        @Bind(R.id.item_explore_comment_user1)
        TextView comment_user1;

        @Bind(R.id.item_explore_comment_user2)
        TextView comment_user2;

        @Bind(R.id.item_explore_comment1)
        TextView comment1;

        @Bind(R.id.item_explore_comment2)
        TextView comment2;

        @Bind(R.id.item_explore_cardView)
        CardView cardView;

        @Bind(R.id.item_explore_comment)
        ImageView commentButton;

        @Bind(R.id.item_explore_thumbup)
        ToggleButton thumbup;

        @Bind(R.id.item_explore_more)
        ImageView exploreMore;

        @Bind(R.id.item_explore_comment1_layout)
        LinearLayout linearLayout1;

        @Bind(R.id.item_explore_comment2_layout)
        LinearLayout linearLayout2;

        public ExploreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void refill(List<ExploreDay> data, boolean flush){
        if(flush) days.clear();
        if(data != null){
            days.addAll(data);
            notifyDataSetChanged();
        }
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
