package com.android.pena.david.news4u.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by david on 05/07/17.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[];
    private Context context;

    public NewsPagerAdapter(FragmentManager fm, Context context, String[] titles) {
        super(fm);
        this.context = context;
        tabTitles = titles;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new MostPopularFragment();
        }else {
            return new MostSharedFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
