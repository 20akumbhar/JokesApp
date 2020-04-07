package com.ajinkya.jokeapp.fragments;


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
import com.ajinkya.jokeapp.adapters.CategoryAdapter;
import com.ajinkya.jokeapp.interfaces.CategoryListener;
import com.ajinkya.jokeapp.models.Category;
import com.ajinkya.jokeapp.viewModels.categoryFetcher;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements CategoryListener {
RecyclerView recyclerView;
CategoryAdapter categoryAdapter;
ArrayList<Category> categories;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.category_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categories=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getActivity(),categories);
        categoryFetcher fetcher=new categoryFetcher(CategoryFragment.this);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onLoaded(ArrayList<Category> categoryList) {
        categories.clear();
        categories.addAll(categoryList);
        categoryAdapter.notifyDataSetChanged();
    }
}
