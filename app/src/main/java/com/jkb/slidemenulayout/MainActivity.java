package com.jkb.slidemenulayout;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.jkb.slidemenu.SlideMenuLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //ui
    private SlideMenuLayout slideMenuLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;

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

        tabLayout = (TabLayout) findViewById(R.id.fmr_tab);
        viewPager = (ViewPager) findViewById(R.id.fmr_vp);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        viewPager.setAdapter(new SlideRightMenuAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

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
