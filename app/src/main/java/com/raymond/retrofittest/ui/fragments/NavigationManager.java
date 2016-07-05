package com.raymond.retrofittest.ui.fragments;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.raymond.retrofittest.R;


/**
 * Created by raymond on 6/5/16.
 */
public class NavigationManager {

    private FragmentManager mFragmentManager;

    public NavigationManager(FragmentManager fragmentManager){
        this.mFragmentManager = fragmentManager;
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
        openAsRoot(fragment);
        return fragment;
    }

    public FindFragment startFind(){
        FindFragment fragment = FindFragment.newInstance();
        openAsRoot(fragment);
        return fragment;

    }

    public Fragment startDrafts(){
        Fragment fragment = DraftsFragment.newInstance();
        openAsRoot(fragment);
        return fragment;

    }

    public Fragment startMore(){
        Fragment fragment = MoreFragement.newInstance();
        openAsRoot(fragment);
        return fragment;
    }


}
