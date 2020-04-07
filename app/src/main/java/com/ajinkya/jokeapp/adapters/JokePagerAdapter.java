package com.ajinkya.jokeapp.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ajinkya.jokeapp.fragments.jokeFragment;
import com.ajinkya.jokeapp.models.Joke;

import java.util.ArrayList;

public class JokePagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Joke> jokes;
    public JokePagerAdapter(FragmentManager fm, ArrayList<Joke> jokes) {
        super(fm);
        this.jokes=jokes;
    }

    @Override
    public Fragment getItem(int position) {
        return new jokeFragment(jokes.get(position));
    }

    @Override
    public int getCount() {
        return jokes.size();
    }
}
