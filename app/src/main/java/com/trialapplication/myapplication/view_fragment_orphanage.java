package com.trialapplication.myapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.util.List;

public class view_fragment_orphanage extends Fragment {

    fragiler dbHelper;
    EditText e1,e2,e3,e4,e5;
    Button b1, b2, b3;
    LinearLayout postContainer;
    orphanageHelper op;
    private static final int SMS_REQUEST_CODE = 123;
    private static final int SMS_PERMISSION_REQUEST_CODE = 456; // Changed permission code

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_orphanage, container, false);
        e1 = view.findViewById(R.id.post_box1);
        e2 = view.findViewById(R.id.name_box1);
        e3 = view.findViewById(R.id.box1);
        e4 = view.findViewById(R.id.phone_box1);
        e5 = view.findViewById(R.id.address_box1);
        b1 = view.findViewById(R.id.p);
        b2 = view.findViewById(R.id.p1);
        b3 = view.findViewById(R.id.sharebutton);
        postContainer = view.findViewById(R.id.post_container);
        dbHelper = new fragiler(getActivity());
        op = new orphanageHelper(requireContext());

        Bundle args = getArguments();
        if (args != null) {
            String action = args.getString("action");
            if ("view_posts".equals(action)) {
                new FetchRecordsTask().execute();
            }
        }

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Your logic for posting
                // You can refresh the records after posting if needed
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Locked successfully", Toast.LENGTH_SHORT).show();
                onLockButtonClick();
            }
        });

        return view;
    }

    private void onLockButtonClick() {
        // Implement logic for lock button click
    }

    private void sendSMS(String phoneNumber, String message) {
        // Check if the app has the SMS permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with sending SMS
            sendSMSInternal(phoneNumber, message);
        }
    }

    // Internal method to send SMS (called when permission is granted)
    private void sendSMSInternal(String phoneNumber, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("address", phoneNumber);
            intent.putExtra("sms_body", message);
            intent.setType("vnd.android-dir/mms-sms");

            // Start activity for result to check if the SMS was sent successfully
            startActivityForResult(intent, SMS_REQUEST_CODE);
        } catch (Exception e) {
            // Opening default SMS app failed, use SmsManager as a fallback
            sendSMSFallback(phoneNumber, message);
        }
    }

    // Fallback method using SmsManager
    private void sendSMSFallback(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getActivity(), "Message sent successfully", Toast.LENGTH_SHORT).show();
            deletePostDetails();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to send message", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Override onActivityResult to handle the result of the SMS action
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SMS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // SMS was sent successfully
                Toast.makeText(getActivity(), "Message sent successfully", Toast.LENGTH_SHORT).show();
                deletePostDetails();
            } else {
                // SMS sending failed or canceled
                Toast.makeText(getActivity(), "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Override onRequestPermissionsResult to handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with sending SMS
                sendSMSInternal("desired_phone_number", "desired_message");
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(getActivity(), "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deletePostDetails() {
        boolean isDeleted = dbHelper.deleteRecord(
                e2.getText().toString(),
                e3.getText().toString(),
                e4.getText().toString(),
                e5.getText().toString()
        );

        if (isDeleted) {
            Toast.makeText(getActivity(), "Post details deleted successfully", Toast.LENGTH_SHORT).show();
            new FetchRecordsTask().execute(); // Refresh the records after deletion
        } else {
            Toast.makeText(getActivity(), "Failed to delete post details", Toast.LENGTH_SHORT).show();
        }
    }

    private class FetchRecordsTask extends AsyncTask<Void, Void, List<Record>> {
        @Override
        protected List<Record> doInBackground(Void... voids) {
            return dbHelper.getAllRecords();
        }

        @Override
        protected void onPostExecute(List<Record> records) {
            super.onPostExecute(records);
            postContainer.removeAllViews();

            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);

                // Create TextViews dynamically for each record
                TextView nameTextView = new TextView(requireContext());
                TextView itemNameTextView = new TextView(requireContext());
                TextView phoneTextView = new TextView(requireContext());
                TextView addressTextView = new TextView(requireContext());

                // Set data from the record to the TextViews
                nameTextView.setText("Name: " + record.getName());
                itemNameTextView.setText("Item Name: " + record.getItemName());
                phoneTextView.setText("Phone: " + record.getPhone());
                addressTextView.setText("Address: " + record.getAddress());

                // Create Lock button dynamically
                Button lockButton = new Button(requireContext());
                lockButton.setText("Lock");
                lockButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handle lock button click
                        Toast.makeText(getActivity(), "Lock button clicked", Toast.LENGTH_SHORT).show();
                        Bundle args = getArguments();

                        if (args != null) {
                            String email = args.getString("email");
                            if (email != null) {
                                Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();
                                String orphanagePhoneNumber = op.getOrphanagePhoneNumber(email);

                                if (orphanagePhoneNumber != null) {
                                    String message = "Post Details: " + record.getName().toString() + ", " + record.getItemName().toString() + ", " + record.getPhone().toString() + ", " + record.getAddress().toString();
                                    Toast.makeText(getActivity(), orphanagePhoneNumber, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    sendSMS(orphanagePhoneNumber, message);
                                } else {
                                    Toast.makeText(getActivity(), "Orphanage phone number is null", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e("ViewFragmentOrphanage", "Email is null");
                            }
                        } else {
                            Log.e("ViewFragmentOrphanage", "Arguments bundle is null");
                        }
                    }
                });

                // Add some spacing between records
                View spacingView = new View(requireContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        16 // Set the desired spacing in pixels
                );
                spacingView.setLayoutParams(params);

                // Set top margin for the first record
                if (i == 0) {
                    LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    nameParams.setMargins(0, 200, 0, 0);
                    nameTextView.setLayoutParams(nameParams);
                }

                // Add the TextViews, Lock button, and spacing to the main container
                postContainer.addView(nameTextView);
                postContainer.addView(itemNameTextView);
                postContainer.addView(phoneTextView);
                postContainer.addView(addressTextView);
                postContainer.addView(lockButton);
                postContainer.addView(spacingView);
            }
        }
    }
}
