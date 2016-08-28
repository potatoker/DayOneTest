package com.raymond.retrofittest.ui.myoneday;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.ui.fragments.BaseFragment;
import com.raymond.retrofittest.ui.fragments.ExploreFragment;
import com.raymond.retrofittest.ui.fragments.MessageFragment;
import com.raymond.retrofittest.ui.fragments.MyFavFragment;
import com.raymond.retrofittest.ui.fragments.MyzoneFragment;
import com.raymond.retrofittest.views.NestedCoordinatorLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 8/15/16.
 */
public class NewFindFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener{
    private static final String TAG = "NewFindFragment";

    public AppBarLayout appbarLayout;

    @Bind(R.id.nested_coord)
    NestedCoordinatorLayout nestedCoordinatorLayout;
    View rootView;
    public static NewFindFragment newInstance() {
        return new NewFindFragment();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_myzone2, null);
        }
        ButterKnife.bind(this, rootView);

        nestedCoordinatorLayout.unlockNestedScrolling();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.find_tabs);
        ViewPager viewPager  = (ViewPager) rootView.findViewById(R.id.find_viewpager);
        appbarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.find_toolbar);

        appbarLayout.addOnOffsetChangedListener(this);

        viewPager.setAdapter(new TabsAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

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
