package com.raymond.retrofittest.ui.fragments;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.ui.myoneday.NewFindFragment;


/**
 * Created by raymond on 6/5/16.
 */
public class NavigationManager {

    private FragmentManager mFragmentManager;

    public NavigationManager(FragmentManager fragmentManager){
        this.mFragmentManager = fragmentManager;
    }

    private void open2(Fragment fragment){
        if(mFragmentManager!=null){
            mFragmentManager.beginTransaction()
                    .replace(R.id.main_container,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    private void open(Fragment fragment){
        if(mFragmentManager != null){
            mFragmentManager.beginTransaction()
                    .replace(R.id.main_container,fragment)
                    .commit();
        }
    }

    private void openAsRoot(Fragment fragment) {
        popEveryFragment();
        open(fragment);
    }

    private void popEveryFragment() {
        // Clear all back stack.
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {

            // Get the back stack fragment id.
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();

            mFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }

    public Fragment startMydays(){
        Fragment fragment = MyDaysFragment.newInstance();
        open2(fragment);
        return fragment;
    }

    public Fragment startFind(){
        RealFindFragment fragment = RealFindFragment.newInstance();
        open2(fragment);
        return fragment;

    }

    public Fragment startDrafts(){
        Fragment fragment = DraftsFragment.newInstance();
        open2(fragment);
        return fragment;

    }

    public Fragment startMore(){
        Fragment fragment = MoreFragement.newInstance();
        open2(fragment);
        return fragment;
    }


}
