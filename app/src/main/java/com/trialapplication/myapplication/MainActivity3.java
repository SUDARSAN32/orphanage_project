package com.trialapplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    EditText e1;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        e1=  (EditText) findViewById(R.id.d1);
        b1 = (Button)findViewById(R.id.button4);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String c = b.getString("name"); // replace "yourKey" with the actual key used in putString
            e1.setText(c );


            b1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String editTextValue = e1.getText().toString();
                    if(editTextValue.equals("User"))
                    {
                        fragment1 frg = new fragment1();
                        Bundle args = new Bundle();
                        args.putString("details", c + " DETAILS");
                        frg.setArguments(args);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.container, frg, "frag");
                        transaction.commit();
                    }
                    else
                    {
                        ORPHAN_FRAG frg1 = new ORPHAN_FRAG();
                        Bundle args = new Bundle();
                        args.putString("details", c + " DETAILS");
                        frg1.setArguments(args);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.container2, frg1, "frag");
                        transaction.commit();
                    }
                }
            });

        }
    }
}
