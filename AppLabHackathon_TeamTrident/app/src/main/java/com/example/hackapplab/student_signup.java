package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class student_signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;

    private EditText reg,name,sem,learnerid,password;
    private Spinner branch;
    private Button signup;

    public student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

        initialize();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    s=new student(reg.getText().toString().trim(),
                            name.getText().toString().trim(),
                            branch.getItemAtPosition(branch.getSelectedItemPosition()).toString(),
                            sem.getText().toString().trim(),
                            learnerid.getText().toString().trim(),
                            "student",
                            false
                            );

                    auth.createUserWithEmailAndPassword(learnerid.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     Intent i=new Intent(getApplicationContext(),verify_account.class);

                                     if(db.push().setValue(s).isSuccessful())
                                         Toast.makeText(student_signup.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                     startActivity(i);
                                     finish();
                                 }
                                 else{
                                     Toast.makeText(student_signup.this, task.getException().toString()+" failed", Toast.LENGTH_SHORT).show();
                                 }
                                }
                            });
                        }else{
                            Toast.makeText(student_signup.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }

            }
        });
    }

    private void initialize(){
        auth= FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference().child("users");

        reg=findViewById(R.id.regno);
        name=findViewById(R.id.name);
        branch=findViewById(R.id.branch);
        sem=findViewById(R.id.sem);
        learnerid=findViewById(R.id.semail);
        password=findViewById(R.id.pswrd);

        signup=findViewById(R.id.psignup_btn);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(adapter);
        branch.setSelected(false);
        branch.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


    }

    private boolean validate(){
        boolean valid=true;
    try {
        if (reg.getText().toString().isEmpty()) {
            reg.setHint("Enter a Valid Registration number");
            valid = false;
        }

        Integer.parseInt(reg.getText().toString());         //TO CHECK IF ITS NUMBER OR NOT

        if(name.getText().toString().isEmpty()){
            name.setHint("Enter a valid name");
            valid=false;
        }

        if(branch.getSelectedItemId()<0){
            Toast.makeText(this, "Select a Branch", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        int s=Integer.parseInt(sem.getText().toString());

        if(s<0 || s>8){
            sem.setHint("Enter Valid semester between 1-8");
            valid=false;
        }

        if(!learnerid.getText().toString().trim().endsWith("@gmail.com")){
            Toast.makeText(this, "Please enter your College learner ID", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        String temp=password.getText().toString().trim();

        if(temp.length()<8){
            Toast.makeText(this, "Password is too weak", Toast.LENGTH_SHORT).show();
            valid=false;
        }
    }
    catch (NumberFormatException e){
        Toast.makeText(this, "RegNo and semester should be in Number Format", Toast.LENGTH_SHORT).show();
        valid = false;
}
    finally {
        return valid;
    }

    }
}
