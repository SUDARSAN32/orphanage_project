package com.trialapplication.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class RecordFragment extends Fragment {


    public static RecordFragment newInstance(String name, String itemName, String phone, String address) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("itemName", itemName);
        args.putString("phone", phone);
        args.putString("address", address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_record, container, false);
        return view;
    }
}
