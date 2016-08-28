package com.raymond.retrofittest.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raymond.retrofittest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 8/15/16.
 */
public class RealFindFragment extends BaseFragment{
    private static final String TAG = "RealFindFragment";

    @Bind(R.id.find_viewpager)
    ViewPager viewPager;

    @Bind(R.id.find_tabs)
    TabLayout tabLayout;

    View rootView;

    public static RealFindFragment newInstance(){
        return new RealFindFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savdInstance){
        if(rootView == null)
            rootView = inflater.inflate(R.layout.fragment_real_find, null);

        ButterKnife.bind(this, rootView);
        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
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
