package com.ajinkya.jokeapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.adapters.JokePagerAdapter;

public class JokeActivity extends AppCompatActivity {
ViewPager viewPager;
JokePagerAdapter jokePagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        viewPager=(ViewPager)findViewById(R.id.joke_pager);
        jokePagerAdapter=new JokePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(jokePagerAdapter);

    }
}
