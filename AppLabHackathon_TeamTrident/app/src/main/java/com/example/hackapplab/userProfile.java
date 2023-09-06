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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class userProfile extends AppCompatActivity {

    private Button done_btn;
    private FirebaseFirestore fb;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private TextView name,email,regno,branch_dept,bd,s1,s2,reg_id;

    private RecyclerView r;

    private ArrayList<uploadd> list;

    private view_adapter s;
    private String Email;

    private ProgressDialog pg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Email = getIntent().getStringExtra("email");
        initialize();
        Toast.makeText(this, "intent"+Email, Toast.LENGTH_SHORT).show();

        pg =new ProgressDialog(this);
        pg.setCancelable(false);
        pg.setMessage("Fetching Data...");
        pg.show();


        fb.collection("upload")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error==null){

                            for(DocumentChange d : value.getDocumentChanges()){
                                if(d.getType() == DocumentChange.Type.ADDED || d.getType() == DocumentChange.Type.MODIFIED) {
                                    list.add(d.getDocument().toObject(uploadd.class));
                                }
                            }
                            s.notifyDataSetChanged();
                            pg.dismiss();
                        }else{
                            Toast.makeText(userProfile.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    private void initialize(){

        list=new ArrayList<>();
        r= findViewById(R.id.recycle_view1);
        r.setHasFixedSize(true);
        s = new view_adapter(userProfile.this,list);
        r.setLayoutManager(new LinearLayoutManager(userProfile.this));
        r.setAdapter(s);
       //r.setLayoutManager(new LinearLayoutManager(userProfile.this));


        fb= FirebaseFirestore.getInstance();
        db = FirebaseDatabase.getInstance().getReference().child("users");

        auth=FirebaseAuth.getInstance();


        done_btn=findViewById(R.id.done_btn);
        reg_id=findViewById(R.id.textView13);
        name=findViewById(R.id.profilename);
        email=findViewById(R.id.profileemail);
        regno=findViewById(R.id.regno);
        branch_dept=findViewById(R.id.branch_dept);
        bd=findViewById(R.id.b_d);
        s1=findViewById(R.id.sem);
        s2=findViewById(R.id.semester);

        email.setText(Email);

        Query q= db.orderByChild("email").equalTo(Email);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot d : snapshot.getChildren()){
                        String cat= d.child("cat").getValue().toString();

                        if(cat.contains("student")){
                            name.setText(d.child("name").getValue().toString());
                            reg_id.setText("Registration Number");
                            regno.setText(d.child("reg").getValue().toString());
                            s1.setText("Semester");
                            s2.setText(d.child("sem").getValue().toString());
                            branch_dept.setText("Branch");
                            bd.setText(d.child("branch").getValue().toString());

                            name.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);
                            reg_id.setVisibility(View.VISIBLE);
                            regno.setVisibility(View.VISIBLE);
                            s1.setVisibility(View.VISIBLE);
                            s2.setVisibility(View.VISIBLE);
                            branch_dept.setVisibility(View.VISIBLE);
                            bd.setVisibility(View.VISIBLE);
                        }
                        else if(cat.contains("professor")){
                            name.setText(d.child("name").getValue().toString());
                            reg_id.setText("ID Number");
                            regno.setText(d.child("id").getValue().toString());
                            branch_dept.setText("Department");
                            bd.setText(d.child("dept").getValue().toString());

                            name.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);
                            reg_id.setVisibility(View.VISIBLE);
                            regno.setVisibility(View.VISIBLE);
                            branch_dept.setVisibility(View.VISIBLE);
                            bd.setVisibility(View.VISIBLE);

                        }
                        else{

                            name.setText(d.child("name").getValue().toString());

                            name.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);


                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(userProfile.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }
}