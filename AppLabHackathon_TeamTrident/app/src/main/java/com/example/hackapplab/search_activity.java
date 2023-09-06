package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;

public class search_activity extends AppCompatActivity {

    ArrayList<user> list;
    FirebaseDatabase db;

    srch_adapter s1;

    RecyclerView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       initialize();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainPage2.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item = menu.findItem(R.id.srch);

        SearchView s = (SearchView) item.getActionView();

        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void initialize(){
        list=new ArrayList<>();
        r=findViewById(R.id.recycleviews);
        r.setHasFixedSize(true);
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        s1= new srch_adapter(getApplicationContext(),list);
        r.setAdapter(s1);

    }
    public void processsearch(String s){

        list=new ArrayList<>();
        db= FirebaseDatabase.getInstance();

        Query q = db.getReference().child("users");

        q.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot d : snapshot.getChildren()){
                        if(d.child("name").getValue().toString().toUpperCase().contains(s.toUpperCase())) {
                            String a1 = d.child("name").getValue().toString();
                            String a2 = d.child("cat").getValue().toString();
                            String a3 = d.child("email").getValue().toString();
                            user u = new user(a1, a2,a3);
                            list.add(u);
                        }
                    }
                    s1.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        r=findViewById(R.id.recycleviews);
        r.setHasFixedSize(true);
        r.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        s1= new srch_adapter(getApplicationContext(),list);
        r.setAdapter(s1);
        }


    }

