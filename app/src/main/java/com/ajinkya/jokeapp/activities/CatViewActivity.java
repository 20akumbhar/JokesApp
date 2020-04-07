package com.ajinkya.jokeapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import static android.view.View.SCROLL_INDICATOR_BOTTOM;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class CatViewActivity extends AppCompatActivity{
RecyclerView recyclerView;
    Toolbar toolbar;
    ProgressBar main,bottom;
    ArrayList<Joke> jokeslist =new ArrayList<>();
    JokeAdapter jokeAdapter;
    private QueryDocumentSnapshot lastvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_view);
        main=findViewById(R.id.catview_progress_main);
        bottom=findViewById(R.id.catview_progress_bottom);
        main.setVisibility(View.VISIBLE);

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
        final String category=getIntent().getStringExtra("category");
        Objects.requireNonNull(getSupportActionBar()).setTitle(category);
        recyclerView=findViewById(R.id.catview_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jokeAdapter = new JokeAdapter(this,jokeslist);
        recyclerView.setAdapter(jokeAdapter);
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .whereEqualTo("Category",category)
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                jokeslist.add(document.toObject(Joke.class));
                                lastvisible=document;
                            }
                            jokeAdapter.notifyDataSetChanged();
                            main.setVisibility(View.GONE);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(SCROLL_INDICATOR_BOTTOM)){
                    bottom.setVisibility(View.VISIBLE);
                    db.collection("Jokes")
                            .whereEqualTo("Category",category)
                            .orderBy("Timestamp", Query.Direction.DESCENDING)
                            .startAfter(lastvisible)
                            .limit(10)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            jokeslist.add(document.toObject(Joke.class));
                                            lastvisible=document;
                                        }
                                        jokeAdapter.notifyDataSetChanged();
                                        bottom.setVisibility(View.GONE);
                                    } else {
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
