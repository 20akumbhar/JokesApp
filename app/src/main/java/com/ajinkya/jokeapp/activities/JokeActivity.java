package com.ajinkya.jokeapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.adapters.JokePagerAdapter;
import com.ajinkya.jokeapp.models.Joke;
import com.ajinkya.jokeapp.viewModels.Fetcher;

import java.util.ArrayList;

public class JokeActivity extends AppCompatActivity {
ViewPager viewPager;
JokePagerAdapter jokePagerAdapter;
ArrayList<Joke> jokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        viewPager=(ViewPager)findViewById(R.id.joke_pager);
        jokes=new ArrayList<>();
        jokePagerAdapter=new JokePagerAdapter(getSupportFragmentManager(),jokes);
        viewPager.setAdapter(jokePagerAdapter);
        int position=getIntent().getIntExtra("position",0);

        Toast.makeText(this, "position from act:"+position, Toast.LENGTH_SHORT).show();
    }
}
