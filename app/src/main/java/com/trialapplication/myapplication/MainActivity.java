package com.trialapplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    Button b1, b2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.editTextTextPersonName3);
        e2 = (EditText) findViewById(R.id.comp1);
        b1 = (Button) findViewById(R.id.button);
        b2=findViewById(R.id.button5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                if (s1.equals(""))
                    e1.setError("Username is empty");
                else if (s2.equals(""))
                    e2.setError("password field is empty");
                else {
                    Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(intent);
                }
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);

            }


        });
    }
}