package com.solu.socketclient;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/*
 * ViewPager 가 껍데기에 불과하므로, 어댑터에서
 * 페이지 정보를 제공해줘야 한다
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter{
    Fragment[] fragments=new Fragment[2];

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments[0]=new ChatFragment();
        fragments[1]=new ConfigFragment();
    }
    public int getCount() {
        return fragments.length;
    }
    public Fragment getItem(int position) {
        return fragments[position];
    }
}
