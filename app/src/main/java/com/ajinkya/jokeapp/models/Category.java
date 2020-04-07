package com.ajinkya.jokeapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class Category {
    String Category,Image;
    int count;
    Timestamp Timestamp;

    public Category() {
    }

    protected Category(Parcel in) {
        Category = in.readString();
        Image = in.readString();
        count = in.readInt();
        Timestamp = in.readParcelable(com.google.firebase.Timestamp.class.getClassLoader());
    }



    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public com.google.firebase.Timestamp getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp = timestamp;
    }

}
