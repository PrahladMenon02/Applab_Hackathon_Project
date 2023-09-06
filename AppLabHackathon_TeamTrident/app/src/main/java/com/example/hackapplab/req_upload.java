package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class req_upload extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText desc;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    private Button up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_upload);

        initialize();


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query q = ref.orderByChild("email").equalTo(auth.getCurrentUser().getEmail());

                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot d: snapshot.getChildren()){
                                uploadd u= new uploadd(
                                        d.child("name").getValue().toString(),
                                        auth.getCurrentUser().getEmail(),
                                        d.child("cat").getValue().toString(),
                                        desc.getText().toString(),
                                        0,
                                        Boolean.valueOf(d.child("verified").getValue().toString()),
                                        Calendar.getInstance().getTimeInMillis()
                                );

                                db.collection("upload").document().set(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(req_upload.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(req_upload.this, "upload failed"+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                        else{
                            Toast.makeText(req_upload.this, "student snapshot failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent it=new Intent(getApplicationContext(),MainPage2.class);
        it.putExtra("opt",0);
        startActivity(it);
        finish();
    }

    private void initialize() {

                ref = FirebaseDatabase.getInstance().getReference("users");
                db = FirebaseFirestore.getInstance();
                desc = findViewById(R.id.editTextTextPersonName);
                up = findViewById(R.id.upload_btn);
                auth = FirebaseAuth.getInstance();

            }
}
