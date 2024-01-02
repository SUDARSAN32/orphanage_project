package com.trialapplication.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class fragment1 extends Fragment {
    TextView textview;
    EditText e1,e2,e3,e4,e5;
    Button b3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        // Initialize the EditText using the inflated view
        textview= view.findViewById(R.id.textView);
        e1=view.findViewById(R.id.editTextTextPersonName);
        e2=view.findViewById(R.id.editTextTextPersonName2);
        e3=view.findViewById(R.id.editTextTextPersonName4);
        e4=view.findViewById(R.id.editTextTextPersonName7);
        e5=view.findViewById(R.id.editTextTextPersonName8);
        b3=view.findViewById(R.id.button3);





        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve data from the bundle
        Bundle args = getArguments();
        if (args != null) {
            // Get the string with key "details" from the bundle
            String details = args.getString("details");

            // Set the retrieved text to the EditText
            textview.setText(details);


        }


    }
}
