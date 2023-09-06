package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Org_signup extends AppCompatActivity {

    private EditText name,email,password;

    private Button signup;

    private FirebaseAuth auth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_signup);

        initialize();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    organization o= new organization(
                      name.getText().toString(),
                      email.getText().toString().trim(),
                      "organization",
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

                                            if(db.push().setValue(o).isSuccessful())
                                                Toast.makeText(Org_signup.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                            startActivity(i);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(Org_signup.this, task.getException().toString()+" failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(Org_signup.this, "Account creation failed", Toast.LENGTH_SHORT).show();
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

        name=findViewById(R.id.oname);
        email=findViewById(R.id.oemail);
        password=findViewById(R.id.opswrd);

        signup=findViewById(R.id.psignup_btn);
    }

    private boolean validate(){

        boolean valid=true;

        if(name.getText().toString().isEmpty()){
            name.setHint("Enter Valid Name");
            valid=false;
        }
        if(email.getText().toString().trim().isEmpty() ||  !email.getText().toString().trim().endsWith("@gmail.com")){
            email.setHint("Enter a valid gmail account");
            valid=false;
        }

        if(password.length()<8){
            password.setHint("Password is Weak");
            valid=false;
        }

        return valid;
    }
}