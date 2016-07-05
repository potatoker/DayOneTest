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
import android.widget.TextView;

import com.raymond.retrofittest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 6/5/16.
 */
public class MoreFragement extends BaseFragment {

    private static final String TAG = "MoreFragement";

//    @Bind(R.id.myDays_text)
//    TextView testText;

    View mRootView;

    @Bind(R.id.v_drafts_viewpager)
    ViewPager viewPager;

    @Bind(R.id.v_tabs)
    TabLayout tabLayout;

    public static MoreFragement newInstance() {
        return new MoreFragement();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View rootView = inflater.inflate(R.layout.fragment_mydays, container, false);
//        ButterKnife.bind(this, rootView);
//        testText.setText("this is find more fragment");
//        return rootView;


        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.viewpager_fragment, null);
        }
        ButterKnife.bind(this, mRootView);

        MyNewAdapter adapter = new MyNewAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return mRootView;
    }




    public static class MyNewAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 2;
        private String titles[] = new String[]{"current day","favorites"};

        public MyNewAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Log.d(TAG, "in the adpter");
            switch (position){
                case 0:
                    InnerFragment1 testFragment = InnerFragment1.newInstance();
                    return testFragment;
                case 1:
                    InnerFragment2 favaFragment = InnerFragment2.newInstance();
                    return favaFragment;
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
