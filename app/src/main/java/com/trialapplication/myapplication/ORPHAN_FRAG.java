package com.trialapplication.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ORPHAN_FRAG extends Fragment  {
    Button b1;
    EditText e1,e2,e3,e4,e5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_r_p_h_a_n__f_r_a_g, container2, false);
        e1=view.findViewById(R.id.editTextTextPersonName15);
        e2=view.findViewById(R.id.editTextTextPersonName13);
        e3=view.findViewById(R.id.editTextTextPersonName6);
        e4=view.findViewById(R.id.editTextTextPersonName5);
        e5=view.findViewById(R.id.editTextTextMultiLine);
        b1=view.findViewById(R.id.button2);

        // Inflate the layout for this fragment
        return view;

    }

}