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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class prof_signup extends AppCompatActivity {

    private EditText id,name,email,password;
    private Spinner dept;

    private Button signup;

    private FirebaseAuth auth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_signup);

        initialize();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    professor p = new professor(
                            id.getText().toString(),
                            name.getText().toString(),
                            dept.getItemAtPosition(dept.getSelectedItemPosition()).toString(),
                            email.getText().toString().trim(),
                            "professor",
                            false
                    );

                    auth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent i=new Intent(getApplicationContext(),verify_account.class);

                                            if(db.push().setValue(p).isSuccessful())
                                                Toast.makeText(prof_signup.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                            startActivity(i);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(prof_signup.this, task.getException().toString()+" failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(prof_signup.this, "Account creation failed", Toast.LENGTH_SHORT).show();
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

        id=findViewById(R.id.pid);
        name=findViewById(R.id.pname);
        email=findViewById(R.id.pemail);
        password=findViewById(R.id.ppswrd);

        signup=findViewById(R.id.psignup_btn);

        dept=findViewById(R.id.pdept);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.dept, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(adapter);
        dept.setSelected(false);
        dept.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

    }
    private boolean validate(){
        boolean valid=true;

        try{
            if(id.getText().toString().isEmpty()){
                id.setHint("ID cannont be empty");
                valid=false;
            }

            if(name.getText().toString().isEmpty()){
                name.setHint("Name cannot be empty");
                valid=false;
            }

            if(!email.getText().toString().trim().endsWith("@gmail.com")){
                email.setHint("Enter a gmail account");
                valid=false;
            }

            if(password.getText().toString().trim().length()<8){
                Toast.makeText(this, "Password is too weak", Toast.LENGTH_SHORT).show();
                valid=false;
            }

        }catch (Exception e){
            valid=false;

        }finally {
            return valid;
        }



    }
}