package com.ajinkya.jokeapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.fragments.CategoryFragment;
import com.ajinkya.jokeapp.fragments.HomeFragment;
import com.ajinkya.jokeapp.fragments.TrendingFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 1:
                return new TrendingFragment();
            case 2:
                return new CategoryFragment();
            default:
                    return new HomeFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Home";
        if (position==1)
            return "Trending";
        if (position==2)
            return "Category";
        return "Home";
    }

    @Override
    public int getCount() {
        return 3;
    }
}