package com.raymond.retrofittest.ui;

import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.raymond.retrofittest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by raymond on 16/4/18.
 */
public class BaseActivity extends AppCompatActivity{

    //Nullable使得该变量可以是null
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @Bind(R.id.app_Logo)
    ImageView appLogo;

    private MenuItem inboxMenuItem;

    public void setContentView(int layoutResID){
        super.setContentView(layoutResID);
        bindViews();

    }

    protected void bindViews(){
        ButterKnife.bind(this);
        setupToolbar();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar(){
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            //设置了navIcon，这个navIcon是toolbar自己集成的一个view，后面还可以特别用
            //setNavigationOnClickListener设置点击事件
            toolbar.setNavigationIcon(R.drawable.ic_menu_white);
        }
    }

    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public Toolbar getToolbar(){
        return toolbar;
    }


    public MenuItem getInboxMenuItem(){
        return inboxMenuItem;
    }

    public ImageView getAppLogo(){
        return appLogo;
    }



}
