package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.admin.v1.Index;

import java.util.ArrayList;
import java.util.Calendar;

public class MainPage2 extends AppCompatActivity {

    private RecyclerView r;
    private FirebaseFirestore fb;
    private view_adapter v;
    private ArrayList<uploadd> list;
    private Button b2,b3,b4;
    private ProgressDialog pg;

    private Spinner s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2);

        pg =new ProgressDialog(this);
        pg.setCancelable(false);
        pg.setMessage("Fetching Data...");
        pg.show();

        initialize();

        Intent it= getIntent();
        int opt = it.getIntExtra("opt",0);

        if(opt==0) {
            fb.collection("upload").orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error == null) {

                                for (DocumentChange d : value.getDocumentChanges()) {
                                    if (d.getType() == DocumentChange.Type.ADDED) {
                                        list.add(d.getDocument().toObject(uploadd.class));
                                    }
                                    v.notifyDataSetChanged();
                                }
                                pg.dismiss();

                            } else {
                                pg.dismiss();
                                Toast.makeText(MainPage2.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else if(opt==1){
            fb.collection("upload").orderBy("timestamp", Query.Direction.DESCENDING).whereEqualTo("category","student")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error == null) {

                                for (DocumentChange d : value.getDocumentChanges()) {
                                    if (d.getType() == DocumentChange.Type.ADDED) {
                                        list.add(d.getDocument().toObject(uploadd.class));
                                    }
                                    v.notifyDataSetChanged();
                                }
                                pg.dismiss();

                            } else {
                                pg.dismiss();
                             //   Toast.makeText(MainPage2.this, "stud" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else if(opt==2){
            fb.collection("upload").orderBy("timestamp", Query.Direction.DESCENDING).whereEqualTo("category","professor")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error == null) {

                                for (DocumentChange d : value.getDocumentChanges()) {
                                    if (d.getType() == DocumentChange.Type.ADDED) {
                                        list.add(d.getDocument().toObject(uploadd.class));
                                    }
                                    v.notifyDataSetChanged();
                                }
                                pg.dismiss();

                            } else {
                                pg.dismiss();
                            //    Toast.makeText(MainPage2.this, "prof" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else if(opt==3){
            fb.collection("upload").orderBy("timestamp", Query.Direction.DESCENDING).whereEqualTo("category","organization")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error == null) {

                                for (DocumentChange d : value.getDocumentChanges()) {
                                    if (d.getType() == DocumentChange.Type.ADDED) {
                                        list.add(d.getDocument().toObject(uploadd.class));
                                    }
                                    v.notifyDataSetChanged();
                                }
                                pg.dismiss();

                            } else {
                                pg.dismiss();
                              //  Toast.makeText(MainPage2.this, "org" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    b2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),search_activity.class));
            finish();
        }
    });

    b3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),req_upload.class));
            finish();
        }
    });

    b4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(),MyProfile.class));
        }
    });

    s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String selecteditem = adapterView.getItemAtPosition(i).toString();

            if(selecteditem.contains("default")){

            }
            else if(selecteditem.contains("recent")){
                Intent it=new Intent(getApplicationContext(),MainPage2.class);
                it.putExtra("opt",0);
                startActivity(it);
            }
            else if(selecteditem.contains("student")){
                Intent it=new Intent(getApplicationContext(),MainPage2.class);
                it.putExtra("opt",1);
                startActivity(it);
            }
            else if(selecteditem.contains("professor")){
                Intent it=new Intent(getApplicationContext(),MainPage2.class);
                it.putExtra("opt",2);
                startActivity(it);
            }
            else if(selecteditem.contains("organization")){
                Intent it=new Intent(getApplicationContext(),MainPage2.class);
                it.putExtra("opt",3);
                startActivity(it);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });
    }

    private void initialize(){
        r = findViewById(R.id.upload_view);
        fb= FirebaseFirestore.getInstance();

        r.setHasFixedSize(true);
        r.setLayoutManager(new LinearLayoutManager((getApplicationContext())));
        list= new ArrayList<>();
        v = new view_adapter(getApplicationContext(),list);
        r.setAdapter(v);

        b2=findViewById(R.id.btn2);
        b3=findViewById(R.id.btn3);
        b4=findViewById(R.id.btn4);


        s= findViewById(R.id.sort_by);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.sortby, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelected(false);
        s.setSelection(0);
        s.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);




    }

}