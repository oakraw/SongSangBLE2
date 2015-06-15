package com.oakraw.songsangble2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oakraw.songsangble2.fragment.CallFriendFragment;
import com.oakraw.songsangble2.fragment.MySongSangFragment;


/**
 * Created by Rawipol on 5/9/15 AD.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private String[] name = {"ส่องแสงของฉัน", "เรียกเพื่อน"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new MySongSangFragment();
            default: return new CallFriendFragment();
        }
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
