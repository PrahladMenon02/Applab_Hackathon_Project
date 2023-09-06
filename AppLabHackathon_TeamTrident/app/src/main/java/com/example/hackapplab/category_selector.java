package com.example.hackapplab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class category_selector extends AppCompatActivity {

    private Button p;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        p = findViewById(R.id.proceed_btn);
        rg= findViewById(R.id.radioGroup);

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(rg.getCheckedRadioButtonId()){

                    case R.id.s:
                        startActivity(new Intent(getApplicationContext(),student_signup.class));
                        finish();
                        break;

                    case R.id.p:
                        startActivity(new Intent(getApplicationContext(),prof_signup.class));
                        finish();
                        break;

                    case R.id.o:
                       startActivity(new Intent(getApplicationContext(),Org_signup.class));
                        finish();
                        break;

                    default:
                        Toast.makeText(category_selector.this, "Please select an Option to proceed", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

    }
}