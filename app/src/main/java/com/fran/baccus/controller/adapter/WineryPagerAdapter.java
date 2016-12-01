package com.fran.baccus.controller.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fran.baccus.controller.fragment.WineFragment;
import com.fran.baccus.model.Winery;


public class WineryPagerAdapter extends FragmentPagerAdapter {

    private Winery mWinery = null;

    public WineryPagerAdapter(FragmentManager fm) {
        super(fm);
        mWinery = Winery.getInstance();
    }

    @Override
    public Fragment getItem(int position) {
        WineFragment fragment = new WineFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE, mWinery.getWine(position));
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public int getCount() {
        return mWinery.getWineCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return mWinery.getWine(position).getName();
    }
}
