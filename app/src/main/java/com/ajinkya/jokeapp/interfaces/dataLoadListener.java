package com.ajinkya.jokeapp.interfaces;

import com.ajinkya.jokeapp.models.Joke;

import java.util.ArrayList;

public interface dataLoadListener {
    void onclicked(ArrayList<Joke> jokes);

    void moreLoaded(ArrayList<Joke> jokes);
}
