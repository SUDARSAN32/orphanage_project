package com.trialapplication.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class fragment1 extends Fragment {
    TextView textview;
    EditText e1, e2, e3, e4, e5;
    Button b3;
    UserHelperClass user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        textview = view.findViewById(R.id.textView);
        e1 = view.findViewById(R.id.editTextTextPersonName);
        e2 = view.findViewById(R.id.editTextTextPersonName2);
        e3 = view.findViewById(R.id.editTextTextPersonName4);
        e4 = view.findViewById(R.id.editTextTextPersonName7);
        e5 = view.findViewById(R.id.editTextTextPersonName8);
        b3 = view.findViewById(R.id.button3);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            String details = args.getString("details");
            textview.setText(details);
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   UserHelperClass user = new UserHelperClass(getActivity());
                    boolean isInserted=user.insertData(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),e4.getText().toString(),e5.getText().toString(),textview.getText().toString());
                    if(isInserted)
                    {
                        Toast.makeText(getActivity(),"user created",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(getActivity(),"something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }




    }
}
