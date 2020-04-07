package com.ajinkya.jokeapp.adapters;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.fragments.HomeFragment;
import com.ajinkya.jokeapp.interfaces.onJokeClicked;
import com.ajinkya.jokeapp.models.Joke;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeHolder> {
    Context context;
    ArrayList<Joke> jokes;
    onJokeClicked callback;
public JokeAdapter(Context context, ArrayList<Joke> jokeslist, Fragment fragment){
    this.context=context;
    this.jokes=jokeslist;
    if(fragment!=null) {
        if (fragment instanceof onJokeClicked) {
            callback = (onJokeClicked) fragment;
        } else {
            throw new RuntimeException(fragment.toString() + "must implement listener");
        }
    }else{
        if (context instanceof onJokeClicked) {
            callback = (onJokeClicked) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement listener");
        }
    }
}
    @NonNull
    @Override
    public JokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item,parent,false);
        return new JokeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeHolder holder, final int position) {
    holder.joke.setText(jokes.get(position).getJoke());
        holder.joke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.JokeClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public class JokeHolder extends RecyclerView.ViewHolder {
        TextView joke;
        ImageButton share,whatsapp,copy,facebook;
        public JokeHolder(@NonNull View itemView) {
            super(itemView);
            joke=(TextView)itemView.findViewById(R.id.joke_textview);
            share=(ImageButton)itemView.findViewById(R.id.sharebtn);
            whatsapp=(ImageButton)itemView.findViewById(R.id.whatsappbtn);
            copy=(ImageButton)itemView.findViewById(R.id.copybtn);
            facebook=(ImageButton)itemView.findViewById(R.id.facebookbtn);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareintent=new Intent(Intent.ACTION_SEND);
                    shareintent.setType("text/plain");
                    shareintent.putExtra(Intent.EXTRA_SUBJECT,"Check this joke");
                    shareintent.putExtra(Intent.EXTRA_TEXT,joke.getText().toString());
                    context.startActivity(Intent.createChooser(shareintent,"Share Joke :"));
                }
            });
            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareintent=new Intent(Intent.ACTION_SEND);
                    shareintent.setType("text/plain");
                    shareintent.setPackage("com.whatsapp");
                    shareintent.putExtra(Intent.EXTRA_TEXT,joke.getText().toString());
                    try {
                        context.startActivity(shareintent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(context, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData=ClipData.newPlainText("Joke",joke.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(context, "copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareintent=new Intent(Intent.ACTION_SEND);
                    shareintent.setType("text/plain");
                    shareintent.setPackage("com.facebook.katana");
                    shareintent.putExtra(Intent.EXTRA_TEXT,joke.getText().toString());
                    try {
                        context.startActivity(shareintent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(context, "something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
