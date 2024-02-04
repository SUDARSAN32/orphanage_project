package com.trialapplication.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ORPHAN_FRAG extends Fragment {
    TextView textview;
    Button b1;
    EditText e1, e2, e3, e4, e5;
    orphanageHelper orphan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_r_p_h_a_n__f_r_a_g, container2, false);
        e1 = view.findViewById(R.id.editTextTextPersonName15);
        e2 = view.findViewById(R.id.editTextTextPersonName13);
        e3 = view.findViewById(R.id.editTextTextPersonName6);
        e4 = view.findViewById(R.id.editTextTextPersonName5);
        e5 = view.findViewById(R.id.editTextTextMultiLine);
        b1 = view.findViewById(R.id.button2);
        textview = view.findViewById(R.id.fun);

        // Inflate the layout for this fragment
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
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString();
                String mobile = e2.getText().toString();
                String email = e3.getText().toString();
                String password = e4.getText().toString();
                String oid = e5.getText().toString();
                MyIntentservice.startActionNumberValidation(requireContext(),oid);
              orphanageHelper orphan=new orphanageHelper(getActivity());
                boolean isInserted =orphan.insertData(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), e5.getText().toString(), textview.getText().toString());
                if (isInserted) {
                    Toast.makeText(getActivity(), "saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // Register a receiver to receive the validation result from the service
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(resultReceiver, new IntentFilter("NumberValidationResult"));
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(resultReceiver);
    }

    private Broadcast_receiver resultReceiver = new Broadcast_receiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            boolean isValid = intent.getBooleanExtra("isValid", false);


            if (isValid) {

                enableButton();
                Toast.makeText(requireContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
            } else {

                disableButton();
                Toast.makeText(requireContext(), "Orphanage Id is not valid", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void enableButton() {

        b1.setEnabled(true);
    }

    private void disableButton() {

        b1.setEnabled(false);
    }
}

