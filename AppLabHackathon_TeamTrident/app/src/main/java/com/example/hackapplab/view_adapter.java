package com.example.hackapplab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_adapter extends RecyclerView.Adapter<view_adapter.MyViewHolder> {

    Context context;
    ArrayList<uploadd> list;

    public view_adapter(Context context, ArrayList<uploadd> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.uitem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        uploadd u = list.get(position);
        holder.username.setText(u.getUser());
        holder.category.setText(u.getCategory());
        holder.desc.setText(u.getDesc());


        if(u.getCategory().contains("student")){
            holder.c.setBackgroundColor(Color.rgb(225,225,225));
        }

        else if(u.getCategory().contains("professor")) {
            holder.c.setBackgroundColor(Color.rgb(150,150,0));
        }
        else if(u.getCategory().contains("organization")){
            holder.c.setBackgroundColor(Color.rgb(0,150,0));
        }



        if (u.getVerified()) {
            holder.verify.setText("Verified User");
            holder.verify.setTextColor(Color.GREEN);
        } else {
            holder.verify.setText("Unverified User");
            holder.verify.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView username, category, desc, verify;
        Button fav,share;

        CardView c;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.recyname);
            category = itemView.findViewById(R.id.recycategory);
            desc = itemView.findViewById(R.id.recydesc);
            share= itemView.findViewById(R.id.share_btn);
            verify = itemView.findViewById(R.id.verified);
            fav = itemView.findViewById(R.id.recyimgbtn);
            c=itemView.findViewById(R.id.root_layout);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "fav btn", Toast.LENGTH_SHORT).show();
                }
            });

            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos= getAdapterPosition();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"Username :\n"+username.getText().toString() +"\n\nDescription :\n"+desc.getText().toString());
            Intent chooserIntent = Intent.createChooser(sharingIntent, null);
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            itemView.getContext().startActivity(chooserIntent);
        }
    }
}
