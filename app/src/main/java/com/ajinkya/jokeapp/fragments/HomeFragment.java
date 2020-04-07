package com.ajinkya.jokeapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.activities.JokeActivity;
import com.ajinkya.jokeapp.adapters.JokeAdapter;
import com.ajinkya.jokeapp.interfaces.dataLoadListener;
import com.ajinkya.jokeapp.interfaces.onJokeClicked;
import com.ajinkya.jokeapp.models.Joke;
import com.ajinkya.jokeapp.viewModels.Fetcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
private RecyclerView recyclerView;
private JokeAdapter jokeAdapter;
ArrayList<Joke> jokeslist;
ProgressBar main,bottom;
    private QueryDocumentSnapshot lastvisible;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.home_recyclerview);
        main=view.findViewById(R.id.home_progress_main);
        bottom=view.findViewById(R.id.home_progress_bottom);
        main.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        jokeslist=new ArrayList<>();
        jokeAdapter=new JokeAdapter(getContext(), jokeslist);
        recyclerView.setAdapter(jokeAdapter);
        final FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
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
                if ( !recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)){
                    bottom.setVisibility(View.VISIBLE);
                    db.collection("Jokes")
                            .orderBy("Timestamp", Query.Direction.DESCENDING)
                            .startAfter(lastvisible)
                            .limit(8)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
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

    }

}
