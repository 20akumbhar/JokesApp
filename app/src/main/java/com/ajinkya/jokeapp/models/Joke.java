package com.ajinkya.jokeapp.models;

import com.google.firebase.Timestamp;

public class Joke {
    String joke,Category;
    boolean like;
    Timestamp Timestamp;
public Joke(){

}
    public Joke(String joke, String category, boolean like, com.google.firebase.Timestamp timestamp) {
        this.joke = joke;
        Category = category;
        this.like = like;
        Timestamp = timestamp;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public com.google.firebase.Timestamp getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp = timestamp;
    }
}
