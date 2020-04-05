package com.ajinkya.jokeapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.activities.CatViewActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    Context context;
    public CategoryAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item,parent,false);
        return new CategoryAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        LinearLayout category;
        TextView cat;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            cat=(TextView)itemView.findViewById(R.id.category);
            category=(LinearLayout)itemView.findViewById(R.id.category_item);
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,CatViewActivity.class);
                    intent.putExtra("category",cat.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
