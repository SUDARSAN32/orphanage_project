package com.trialapplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    ImageButton b1,b2;
    String selected;
    TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b1=(ImageButton) findViewById(R.id.imageButton6);
        b2=(ImageButton) findViewById(R.id.imageButton4);
        t1=findViewById(R.id.textView13);
        t2=findViewById(R.id.textView11);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                selected=t1.getText().toString();
                Bundle b=new Bundle();
                b.putString("name",selected);
                intent.putExtras(b);
                startActivity(intent);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                selected=t2.getText().toString();
                Bundle b=new Bundle();
                b.putString("name",selected);
                intent.putExtras(b);
                startActivity(intent);

            }
        });
    }
}