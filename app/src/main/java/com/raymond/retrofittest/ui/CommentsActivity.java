package com.raymond.retrofittest.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.raymond.retrofittest.DataPresenter;
import com.raymond.retrofittest.R;
import com.raymond.retrofittest.adapters.CommentsAdapter;
import com.raymond.retrofittest.datatype.Comment;
import com.raymond.retrofittest.datatype.User;
import com.raymond.retrofittest.utils.Utils;
import com.raymond.retrofittest.views.SendCommentButton;

import java.util.ArrayList;

import butterknife.Bind;

public class CommentsActivity extends BaseActivity implements SendCommentButton.OnSendClickListener, DataPresenter.PostComment{


    private static final String TAG = "CommentsActivity";
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    public static final String KEY_DAY_ID = "key_day_id";
    public static final String KEY_COMMENTS = "key_comments";


    @Bind(R.id.contentRoot)
    LinearLayout contentRoot;
    @Bind(R.id.rvComments)
    RecyclerView rvComments;
    @Bind(R.id.llAddComment)
    LinearLayout llAddComment;
    @Bind(R.id.etComment)
    EditText etComment;
    @Bind(R.id.btnSendComment)
    SendCommentButton btnSendComment;

    private CommentsAdapter commentsAdapter;
    private int drawingStartLocation;

    ArrayList<Comment> comments;
    String dayId;




    public static void openWithDayId(Context openingActivity, String dayId, ArrayList<Comment> comments){
        Intent intent = new Intent(openingActivity, CommentsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_COMMENTS, comments);
        intent.putExtra(KEY_DAY_ID, dayId);
        intent.putExtras(bundle);
        openingActivity.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        if(savedInstanceState==null){
            dayId = getIntent().getStringExtra(KEY_DAY_ID);
            Bundle bundle = getIntent().getExtras();
            comments = bundle.getParcelableArrayList(KEY_COMMENTS);
        }else{
            dayId = savedInstanceState.getParcelable(KEY_DAY_ID);
            Bundle bundle = getIntent().getExtras();
            comments = savedInstanceState.getParcelableArrayList(KEY_COMMENTS);
        }


        setupComments();
        setupSendCommentButton();

        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }
    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(this,comments);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });
    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    private void startIntroAnimation() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(200);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(getToolbar(), Utils.dpToPx(8));
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        commentsAdapter.updateItems();
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CommentsActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
//            commentsAdapter.addItem();
            User user = User.getInstance();
            Comment comment = new Comment(user.getUid(),user.getName(),etComment.getText().toString(),dayId, user.getAvatarurl());
            comments.add(comment);
            commentsAdapter.notifyDataSetChanged();
//            commentsAdapter.refill(comments,true);
            DataPresenter.PostComment(comment, this);

            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);

            if(comments.size()==0){
                Log.d(TAG,"comment list size is 0");
                Log.d(TAG, comment.getComment());
            }else{
                Log.d(TAG, "comment size is:" + comments.size());
            }

            if(rvComments.getChildAt(0)==null){
                Log.d(TAG, "child at 0 is null");
                Log.d(TAG, "item count is "+ commentsAdapter.getItemCount());
            }else {
                rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

            }
            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }

    @Override
    public void onPostComment(int i, Boolean ok) {
        if(ok){
            Toast.makeText(this, "评论成功",
                    Toast.LENGTH_LONG).show();
        }
    }

}
