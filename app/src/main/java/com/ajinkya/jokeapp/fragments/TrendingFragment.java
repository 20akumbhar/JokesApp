package com.ajinkya.jokeapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.activities.JokeActivity;
import com.ajinkya.jokeapp.adapters.JokeAdapter;
import com.ajinkya.jokeapp.interfaces.dataLoadListener;
import com.ajinkya.jokeapp.interfaces.onJokeClicked;
import com.ajinkya.jokeapp.models.Joke;
import com.ajinkya.jokeapp.viewModels.Fetcher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment implements dataLoadListener, onJokeClicked {
RecyclerView recyclerView;
JokeAdapter jokeAdapter;
ArrayList<Joke> jokeslist;
Fetcher fetcher;
    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.trending_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        jokeslist=new ArrayList<>();
        jokeAdapter=new JokeAdapter(getContext(), jokeslist, TrendingFragment.this);
        fetcher=new Fetcher(TrendingFragment.this);
        fetcher.getTrendData();
        recyclerView.setAdapter(jokeAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)){
                    fetcher.getMoreTrendData();
                }
            }
        });
    }

    @Override
    public void onclicked(ArrayList<Joke> jokes) {
        jokeslist.clear();
        jokeslist.addAll(jokes);
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void moreLoaded(ArrayList<Joke> jokes) {
        jokeslist.addAll(jokes);
        jokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void JokeClicked(int position) {
        Intent intent=new Intent(getContext(), JokeActivity.class);
        intent.putExtra("position",position);
        getContext().startActivity(intent);
    }
}
