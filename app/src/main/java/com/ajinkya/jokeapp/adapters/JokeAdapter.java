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
import androidx.recyclerview.widget.RecyclerView;

import com.ajinkya.jokeapp.R;
import com.ajinkya.jokeapp.activities.JokeActivity;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeHolder> {
    Context context;
public JokeAdapter(Context context){
    this.context=context;
}
    @NonNull
    @Override
    public JokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item,parent,false);
        return new JokeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
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
            joke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, JokeActivity.class));
                }
            });
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
