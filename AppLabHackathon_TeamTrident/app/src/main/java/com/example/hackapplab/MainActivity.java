package com.example.hackapplab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView reg,forgot;

    private EditText username, password;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),category_selector.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()){


                    auth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (auth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(MainActivity.this, "Verified and login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainPage.class));
                                } else {
                                    Toast.makeText(MainActivity.this, "Verify Email before Proceeding", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (task.getException().toString().contains("InvalidCredentials")) {
                                    Toast.makeText(MainActivity.this, "OOPS! The credentials entered seem to be wrong :( Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

            }
        }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText reset = new EditText(view.getContext());
                AlertDialog.Builder passwordreset = new AlertDialog.Builder(view.getContext());
                passwordreset.setTitle("Reset Password?");
                passwordreset.setView(reset);

                passwordreset.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = reset.getText().toString().trim();
                        boolean flag=true;
                        if(email.isEmpty() || !email.endsWith("@gmail.com")){
                            reset.setHint("Enter valid gmail account");
                            flag=false;
                        }

                        if(flag){
                            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        Toast.makeText(view.getContext(), "Reset email has been sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(view.getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });

                passwordreset.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                passwordreset.create().show();
            }
        });
    }
    private void initialize(){

        reg=findViewById(R.id.register);
        forgot=findViewById(R.id.resetpswrd);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        login=findViewById(R.id.login_btn);

        auth= FirebaseAuth.getInstance();

    }
    private boolean validate(){
        boolean valid=true;

        if(username.getText().toString().isEmpty() || !username.getText().toString().endsWith("@gmail.com")){
            valid=false;
            Toast.makeText(this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        }

        if(password.getText().toString().isEmpty()){
            valid=false;
            Toast.makeText(this, "Enter valid password", Toast.LENGTH_SHORT).show();
        }

        return valid;

    }
}