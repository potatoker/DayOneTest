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
import com.raymond.retrofittest.ui.MyNewDaysActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 6/4/16.
 */
public class DraftsFragment extends BaseFragment{

    private static final String TAG = "DraftsFragment";


    @Bind(R.id.drafts_viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    View rootView;


    public static DraftsFragment newInstance(){
        DraftsFragment f = new DraftsFragment();
        return f;
    }

//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_draft, container, false);
        }

        ButterKnife.bind(this, rootView);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }


//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
////        ButterKnife.bind(this, rootView);
//
//        viewPager = (ViewPager) view.findViewById(R.id.drafts_viewpager_v);
//        tabLayout = (TabLayout) view.findViewById(R.id.tabs_v);
//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);
//
//    }


    private void setupViewPager(ViewPager viewPager){
        MyNewAdapter adapter = new MyNewAdapter(getChildFragmentManager());
//        adapter.addFragment(TestFragment.newInstance(), "current day");
//        adapter.addFragment(new FavaFragment(), "favorite moments");
        viewPager.setAdapter(adapter);


    }

    public static class MyAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        public int getCount(){
            return mFragmentList.size();
        }

        public Fragment getItem(int position){
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }


    public static class MyNewAdapter extends FragmentPagerAdapter{

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
                    CurrentDayFragment currentDayFragment = CurrentDayFragment.newInstance();
                    return currentDayFragment;
                case 1:
                    FavaFragment favaFragment = FavaFragment.newInstance();
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


//    public void onDetach() {
//        super.onDetach();
//        try {
//            Field childFragmentManager = Fragment.class
//                    .getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
