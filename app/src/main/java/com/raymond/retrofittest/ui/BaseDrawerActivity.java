package com.raymond.retrofittest.ui;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.raymond.retrofittest.R;

import butterknife.Bind;
import butterknife.BindDimen;

public class BaseDrawerActivity extends BaseActivity {

    private static final String TAG = "BaseDrawerActivity";

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @BindDimen(R.dimen.avatar_size)
    int avatarSize;

    //由于要用picasso加载图片所以不再这里绑定
    //上面是Ins中的解释，但是，为什么不可以呢？不都是先绑定，再设置src吗
    private ImageView headerImageView;

    public void setContentView(int layoutResID){
        super.setContentViewWithoutInject(R.layout.activity_base_drawer);

        //注意到其实这样就实现了一个drawer的layout但是全球通用，因为产生的新的activity将会
        //被嵌进activity_base_drawer的main_content里面
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.main_content);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup,true);
        bindViews();
        setupHeader();
        initDrawer();

    }

    protected void setupToolbar(){
        super.setupToolbar();
        if(getToolbar() != null){
            //点击toolbar最左边的nav图标的时候就会打开drawer
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    private void setupHeader(){
        View headerView = navigationView.getHeaderView(0);
        if(headerView == null){

            Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();

        }
        headerImageView = (ImageView) headerView.findViewById(R.id.headerImg);
        headerView.findViewById(R.id.globalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGloableMenuHeaderClick(v);
            }
        });

        headerImageView.setImageResource(R.drawable.ic_header_profile);

    }

    private void initDrawer(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.myDay:
                        return true;

                    case R.id.startNew:
                        return true;

                    case R.id.dayInProgress:
                        return true;
                }


                return false;
            }
        });

//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
//                drawerLayout,getToolbar(),
//                R.string.app_name,R.string.action_settings){
//
//            public void onDrawerClosed(View drawerView){
//                super.onDrawerClosed(drawerView);
//            }
//
//            public void onDrawerOpened(View drawerView){
//                super.onDrawerOpened(drawerView);
//            }
//
//        };
//
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();

    }

    public void onGloableMenuHeaderClick(final View v){
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                MyDaysActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);

    }

}
