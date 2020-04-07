package com.ajinkya.jokeapp.viewModels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ajinkya.jokeapp.interfaces.CategoryListener;
import com.ajinkya.jokeapp.models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class categoryFetcher {
    public ArrayList<Category> categories;
    public CategoryListener listener;
    public categoryFetcher(Fragment context){
        if(context instanceof CategoryListener){
            listener=(CategoryListener) context;
        }else {
            throw new RuntimeException(context.toString()+"must implement listener");
        }
        categories=new ArrayList<>();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                categories.add(document.toObject(Category.class));
                            }
                            listener.onLoaded(categories);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
