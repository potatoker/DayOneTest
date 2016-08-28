package com.raymond.retrofittest.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.ui.MyNewDaysActivity;
import com.raymond.retrofittest.views.NestedCoordinatorLayout;
import com.roughike.bottombar.BottomBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 6/5/16.
 */
public class FindFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener{
    private static final String TAG = "FindFragment";

    private ViewPropertyAnimatorCompat mTranslationAnimator;

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private ImageView mProfileImage;
    private int mMaxScrollSize;

    public BottomBar bottomBar;

    public NestedCoordinatorLayout parentCor;
    public AppBarLayout appbarLayout;

//    @Bind(R.id.nest_cord)
//    NestedCoordinatorLayout nestedCoordinatorLayout;
    public static NestedCoordinatorLayout nestedCoordinatorLayout;

    View rootView;


    private MyNewDaysActivity parentActivity;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_find, null);
        }
        ButterKnife.bind(this, rootView);
        nestedCoordinatorLayout = (NestedCoordinatorLayout) rootView.findViewById(R.id.nest_cord);

        nestedCoordinatorLayout.unlockNestedScrolling();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.find_tabs);
        ViewPager viewPager  = (ViewPager) rootView.findViewById(R.id.find_viewpager);
        appbarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
        mProfileImage = (ImageView) rootView.findViewById(R.id.find_profile_image);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.find_toolbar);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                onBackPressed();
//            }
//        });

//        toolbar.setTitle("sdfasdfsf");
        appbarLayout.addOnOffsetChangedListener(this);
//        appbarLayout.addOnOffsetChangedListener(parentActivity);

        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);





        return rootView;
    }


    public void onAttach(Context context){
        super.onAttach(context);
        Activity a = null;

        if (context instanceof Activity){
            a=(Activity) context;
        }

        if( a instanceof MyNewDaysActivity){
            parentActivity = (MyNewDaysActivity) a;
        }

    }

//    public void onActivityCreated(Bundle savedInstance){
//
//    }

    public AppBarLayout getAppbarLayout(){
        return appbarLayout;
    }





    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        Log.d(TAG, "i is:"+ i);

        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        Log.d(TAG, "max is :" + mMaxScrollSize);

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();


//            if(i >= mMaxScrollSize) {
//                nestedCoordinatorLayout.lockNestedScrolling();
//            }

        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();

//            nestedCoordinatorLayout.unlockNestedScrolling();
        }


//        if(Math.abs(i) == mMaxScrollSize){
//            nestedCoordinatorLayout.lockNestedScrolling();
//            Log.d(TAG, "==:" + i);
//
//        }else if(Math.abs(i) < mMaxScrollSize){
//            nestedCoordinatorLayout.unlockNestedScrolling();
//
//            Log.d(TAG, "<:" + i);
//            parentCor.lockNestedScrolling();
//        }


    }


    class TabsAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 4;
        private String titles[] = new String[]{"my zone","explore","favs","messages"};

        public TabsAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Log.d(TAG, "in the adpter");
            switch (position){
                case 0:
                    MyzoneFragment myzoneFragment = MyzoneFragment.newInstance();
                    return myzoneFragment;
                case 1:
                    ExploreFragment exploreFragment = ExploreFragment.newInstance();
                    return exploreFragment;
                case 2:
                    MyFavFragment myFavFragment = MyFavFragment.newInstance();
                    return myFavFragment;
                case 3:
                    MessageFragment messageFragment = MessageFragment.newInstance();
                    return messageFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public CharSequence getPageTitle(int position){
            return titles[position];
        }
    }

}
