package com.jkb.slidemenulayout;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.jkb.slidemenu.SlideMenuLayout;
import com.jkb.slidemenulayout.adapter.ContentAdapter;
import com.jkb.slidemenulayout.adapter.SlideLeftAdapter;
import com.jkb.slidemenulayout.adapter.SlideRightMenuAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ui
    private SlideMenuLayout slideMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusBar();
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        slideMenuLayout = (SlideMenuLayout) findViewById(R.id.mainSlideMenu);

        //right
        TabLayout slideTabLayout = (TabLayout) findViewById(R.id.fmr_tab);
        ViewPager slideViewPager = (ViewPager) findViewById(R.id.fmr_vp);
        slideTabLayout.addTab(slideTabLayout.newTab());
        slideTabLayout.addTab(slideTabLayout.newTab());
        slideViewPager.setAdapter(new SlideRightMenuAdapter(getSupportFragmentManager()));
        slideTabLayout.setupWithViewPager(slideViewPager);
        //left
        RecyclerView leftRecyclerView = (RecyclerView) findViewById(R.id.cml_rv);
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SlideLeftAdapter slideLeftAdapter = new SlideLeftAdapter(this);
        leftRecyclerView.setAdapter(slideLeftAdapter);
        //content
        TabLayout contentTabLayout = (TabLayout) findViewById(R.id.am_tab);
        ViewPager contentViewPager = (ViewPager) findViewById(R.id.am_vp);
        contentTabLayout.addTab(contentTabLayout.newTab());
        contentTabLayout.addTab(contentTabLayout.newTab());
        contentViewPager.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        contentTabLayout.setupWithViewPager(contentViewPager);

        findViewById(R.id.fm_leftMenu).setOnClickListener(this);
        findViewById(R.id.fm_rightMenu).setOnClickListener(this);
    }

    /**
     * 初始化沉浸式状态栏
     */
    private void initStatusBar() {
        //设置是否沉浸式
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        int flag_translucent_status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //透明状态栏
        getWindow().setFlags(flag_translucent_status, flag_translucent_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fm_leftMenu:
                slideMenuLayout.toggleLeftSlide();
                break;
            case R.id.fm_rightMenu:
                slideMenuLayout.toggleRightSlide();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (slideMenuLayout.isLeftSlideOpen() || slideMenuLayout.isRightSlideOpen()) {
            slideMenuLayout.closeLeftSlide();
            slideMenuLayout.closeRightSlide();
        } else {
            super.onBackPressed();
        }
    }
}
