package com.raymond.retrofittest.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.datatype.Comment;
import com.raymond.retrofittest.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 8/29/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CommentsAdapter";

    private Context context;

    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    ArrayList<Comment> comments;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        CommentViewHolder holder = (CommentViewHolder) viewHolder;

        holder.tvComment.setText(comments.get(position).getComment());

        Picasso.with(context)
                .load(comments.get(position).getProfileImgUrl())
                .centerCrop()
                .resize(avatarSize, avatarSize)
                .transform(new RoundedTransformation())
                .into(holder.ivUserAvatar);
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateItems() {
//        itemsCount = 10;
        notifyDataSetChanged();
    }

//    public void addItem() {
//        itemsCount++;
//        notifyItemInserted(itemsCount - 1);
//    }

    public void refill(List<Comment> data, boolean flush){
        if(flush) comments.clear();
        if(data!=null){
            Log.d(TAG,"data not null ");
            comments.addAll(data);
            Log.d(TAG, ""+comments.size());
            notifyDataSetChanged();
        }else{
            Log.d(TAG,"data is null!!!!!!!");
        }
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivUserAvatar)
        ImageView ivUserAvatar;
        @Bind(R.id.tvComment)
        TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}