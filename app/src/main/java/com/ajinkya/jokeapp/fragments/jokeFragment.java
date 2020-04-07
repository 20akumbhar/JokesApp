package com.ajinkya.jokeapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.models.Joke;

/**
 * A simple {@link Fragment} subclass.
 */
public class jokeFragment extends Fragment {
    Joke joke;
public jokeFragment(){

}

    public jokeFragment(Joke joke) {
        // Required empty public constructor
        this.joke=joke;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView jokeview=view.findViewById(R.id.joke_full);
        jokeview.setText(joke.getJoke());
    }
}
