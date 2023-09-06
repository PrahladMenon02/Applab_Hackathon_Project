package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainPage extends AppCompatActivity {
    private Button done_btn;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private ProgressDialog pg;
    private TextView name,email,regno,branch_dept,bd,s1,s2,reg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        pg =new ProgressDialog(this);
        pg.setCancelable(false);
        pg.setMessage("Fetching Data...");
        pg.show();

        initialize();

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),MainPage2.class);

                it.putExtra("opt",0);

                startActivity(it);
                finish();
            }
        });

    }

    private void initialize(){
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


        String Email= auth.getCurrentUser().getEmail();
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

            }
        });

        pg.dismiss();
    }
}