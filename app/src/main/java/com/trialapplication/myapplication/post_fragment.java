package com.trialapplication.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class post_fragment extends Fragment {

    fragiler fh;
    EditText e1, e2, e3, e4,e5;
    Button b1,b2,b3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_fragment, container, false);
        e1 = view.findViewById(R.id.post_box);
        e2 = view.findViewById(R.id.name_box);
        e3 = view.findViewById(R.id.box);
        e4 = view.findViewById(R.id.phone_box);
        e5 = view.findViewById(R.id.address_box);
        b1 = view.findViewById(R.id.p);
        b2= view.findViewById(R.id.p1);
        b3 = view.findViewById(R.id.sharebutton);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Button2", "Button 2 clicked");

               fh = new fragiler(getActivity());
               boolean isInserted=fh.insertData(e2.getText().toString(),e3.getText().toString(),e4.getText().toString(),e5.getText().toString());
               if(isInserted)
                {
                    Toast.makeText(getActivity(),"posted successfully",Toast.LENGTH_SHORT).show();
                }
               else{
                   Toast.makeText(getActivity(),"something went wrong",Toast.LENGTH_SHORT).show();
               }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Button3", "Button 3 clicked");
                shareContent();
            }
        });
    }


    private void shareContent() {
        String nameText = e2.getText().toString();
        String boxText = e3.getText().toString();
        String phoneText = e4.getText().toString();
        String addressText = e5.getText().toString();

        String contentToShare = "Name: " + nameText + "\n"
                + "Box: " + boxText + "\n"
                + "Phone: " + phoneText + "\n"
                + "Address: " + addressText;
        Log.d("ShareFragment", "shareContent called with content: " + contentToShare);


        insertContent(contentToShare);


        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);


        sendIntent.setType("text/plain");


        sendIntent.putExtra(Intent.EXTRA_TEXT, MyContentProvider.CONTENT_URI.toString());

        Intent shareIntent = Intent.createChooser(sendIntent, null);

        if (sendIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(shareIntent);
        }
        else {
            Log.e("ShareFragment", "No activity found to handle the Intent.");
        }
    }

    private void insertContent(String content) {
        // Insert the content into the ContentProvider
        ContentValues values = new ContentValues();
        values.put("content", content);
        Log.d("ShareFragment", "insertContent called with content: " + content);

        Uri contentUri = MyContentProvider.CONTENT_URI;
        requireActivity().getContentResolver().insert(contentUri, values);
    }
}
