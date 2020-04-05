package com.ajinkya.jokeapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ajinkya.jokeapp.fragments.jokeFragment;

public class JokePagerAdapter extends FragmentStatePagerAdapter {
    public JokePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new jokeFragment();
    }

    @Override
    public int getCount() {
        return 20;
    }
}
