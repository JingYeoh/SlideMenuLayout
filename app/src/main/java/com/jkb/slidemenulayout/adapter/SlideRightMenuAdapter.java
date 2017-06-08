package com.jkb.slidemenulayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkb.slidemenulayout.ui.ChatFragment;
import com.jkb.slidemenulayout.ui.FriendFragment;

/**
 * 右滑菜单适配器
 * Created by yangjing on 17-6-7.
 */

public class SlideRightMenuAdapter extends FragmentPagerAdapter {

    private String[] menuNames = new String[]{
            "chat", "friend"
    };

    public SlideRightMenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ChatFragment.newInstance();
        } else {
            return FriendFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return menuNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return menuNames[position];
    }
}
