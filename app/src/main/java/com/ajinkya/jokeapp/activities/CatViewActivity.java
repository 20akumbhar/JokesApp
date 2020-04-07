package com.ajinkya.jokeapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.adapters.JokeAdapter;
import com.ajinkya.jokeapp.interfaces.dataLoadListener;
import com.ajinkya.jokeapp.interfaces.onJokeClicked;
import com.ajinkya.jokeapp.models.Joke;
import com.ajinkya.jokeapp.viewModels.Fetcher;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Objects;

public class CatViewActivity extends AppCompatActivity implements dataLoadListener, onJokeClicked {
RecyclerView recyclerView;
    Toolbar toolbar;
Fetcher fetcher;
ArrayList<Joke> jokeslist =new ArrayList<>();
    JokeAdapter jokeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_view);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        toolbar=findViewById(R.id.toolbar_cat);
        setSupportActionBar(toolbar);
        String category=getIntent().getStringExtra("category");
        Objects.requireNonNull(getSupportActionBar()).setTitle(category);
        recyclerView=findViewById(R.id.catview_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jokeAdapter = new JokeAdapter(this,jokeslist, null);
        recyclerView.setAdapter(jokeAdapter);
        fetcher=new Fetcher(CatViewActivity.this);
        fetcher.getCatdata(category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onclicked(ArrayList<Joke> jokes) {
        Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show();
        jokeslist.clear();
        jokeslist.addAll(jokes);
        for(Joke joke:jokeslist){
            Log.e("dddd",joke.getCategory());
        }
        Log.v("notified","yes");
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void moreLoaded(ArrayList<Joke> jokes) {

    }


    @Override
    public void JokeClicked(int position) {
        Intent intent=new Intent(CatViewActivity.this,JokeActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
