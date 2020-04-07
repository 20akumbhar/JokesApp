package com.ajinkya.jokeapp.viewModels;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ajinkya.jokeapp.activities.CatViewActivity;
import com.ajinkya.jokeapp.fragments.HomeFragment;
import com.ajinkya.jokeapp.interfaces.dataLoadListener;
import com.ajinkya.jokeapp.models.Joke;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Fetcher implements Parcelable {
    public ArrayList<Joke> jokes;
    public ArrayList<Joke> morejokes;
    public dataLoadListener listener;
    QueryDocumentSnapshot lastvisible;
    public Fetcher(Context context){
        if(context instanceof dataLoadListener){
            listener=(dataLoadListener)context;
        }else {
            throw new RuntimeException(context.toString()+"must implement listener");
        }
        jokes=new ArrayList<>();
        morejokes=new ArrayList<>();

    }

    public Fetcher(Fragment fragment) {
        if(fragment instanceof dataLoadListener){
            listener=(dataLoadListener)fragment;
        }else {
            throw new RuntimeException(fragment.toString()+"must implement listener");
        }
        jokes=new ArrayList<>();
        morejokes=new ArrayList<>();

    }

    protected Fetcher(Parcel in) {
    }

    public static final Creator<Fetcher> CREATOR = new Creator<Fetcher>() {
        @Override
        public Fetcher createFromParcel(Parcel in) {
            return new Fetcher(in);
        }

        @Override
        public Fetcher[] newArray(int size) {
            return new Fetcher[size];
        }
    };

    public void getdata(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                jokes.add(document.toObject(Joke.class));
                                lastvisible=document;
                            }
                            listener.onclicked(jokes);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void getMoreData(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .startAfter(lastvisible)
                .limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            morejokes.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                morejokes.add(document.toObject(Joke.class));
                                lastvisible=document;
                            }
                            listener.moreLoaded(morejokes);
                            jokes.addAll(morejokes);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void getCatdata(String category){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .whereEqualTo("Category",category)
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                jokes.add(document.toObject(Joke.class));
                            }
                            listener.onclicked(jokes);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void getTrendData(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .whereEqualTo("like",true)
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                jokes.add(document.toObject(Joke.class));
                                lastvisible=document;
                            }
                            listener.onclicked(jokes);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void getMoreTrendData(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Jokes")
                .whereEqualTo("like",true)
                .orderBy("Timestamp", Query.Direction.DESCENDING)
                .startAfter(lastvisible)
                .limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            morejokes.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                morejokes.add(document.toObject(Joke.class));
                                lastvisible=document;
                            }
                            listener.moreLoaded(morejokes);
                            jokes.addAll(morejokes);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
    public ArrayList<Joke> getData(){
        return jokes;
    }
}
