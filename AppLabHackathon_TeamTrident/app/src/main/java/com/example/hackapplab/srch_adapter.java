package com.example.hackapplab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class srch_adapter extends RecyclerView.Adapter<srch_adapter.MyViewHolder> {
    Context context;
    ArrayList<user> list;
    static ArrayList<user> l;

    public srch_adapter(Context context, ArrayList<user> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sitems, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        user u = list.get(position);
        l=list;
        holder.username.setText(u.getUsername());
        holder.cat.setText(u.getCategory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username,cat;
        Button share;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.textView9);
            cat= itemView.findViewById(R.id.scat);
            share=itemView.findViewById(R.id.search_sharebtn);

            share.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "item clicked"+l.get(getAdapterPosition()).getEmail(), Toast.LENGTH_SHORT).show();
                    Intent it=new Intent(view.getContext(),MyProfile.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    it.putExtra("email",l.get(getAdapterPosition()).getEmail());
                    view.getContext().startActivity(it);

                }
            });

        }

        @Override
        public void onClick(View view) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"Trident Username :\n"+username.getText().toString()
            +"\nCategory :\n"+cat.getText().toString());
            Intent chooserIntent = Intent.createChooser(sharingIntent, null);
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            itemView.getContext().startActivity(chooserIntent);

        }
    }

}
