package com.ajinkya.jokeapp.interfaces;

import com.ajinkya.jokeapp.models.Category;

import java.util.ArrayList;

public interface CategoryListener {
    void onLoaded(ArrayList<Category> categories);
}
