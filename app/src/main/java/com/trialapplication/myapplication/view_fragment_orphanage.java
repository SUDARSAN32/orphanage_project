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
    EditText e1, e2, e3, e4, e5;
    Button b1, b2, b3;
    LinearLayout postContainer;
    orphanageHelper op;
    private static final int SMS_REQUEST_CODE = 123;
    private static final int SMS_PERMISSION_REQUEST_CODE = 456;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_orphanage, container, false);
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

        return view;
    }

    private void sendSMS(String phoneNumber, String message) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            sendSMSInternal(phoneNumber, message);
        }
    }

    private void sendSMSInternal(String phoneNumber, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("address", phoneNumber);
            intent.putExtra("sms_body", message);
            intent.setType("vnd.android-dir/mms-sms");

            startActivityForResult(intent, SMS_REQUEST_CODE);
        } catch (Exception e) {
            sendSMSFallback(phoneNumber, message);
        }
    }

    private void sendSMSFallback(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getActivity(), "Message sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Failed to send message", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SMS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "Message sent successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Replace the desired_phone_number and desired_message with actual values
                sendSMSInternal("desired_phone_number", "desired_message");
            } else {
                Toast.makeText(getActivity(), "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deletePostDetails(String k) {
        Log.d("ViewFragmentOrphanage", "deletePostDetails() called");
        if (k != null) {
            Log.d("ViewFragmentOrphanage", "Deleting post details with phone: " + k);
            boolean isDeleted = dbHelper.deleteRecord(k);

            if (isDeleted) {
                Log.d("ViewFragmentOrphanage", "Post details deleted successfully");
                Toast.makeText(getActivity(), "Post details deleted successfully", Toast.LENGTH_SHORT).show();
                new FetchRecordsTask().execute();
            } else {
                Log.e("ViewFragmentOrphanage", "Failed to delete post details");
                Toast.makeText(getActivity(), "Failed to delete post details", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("ViewFragmentOrphanage", "UI element (e4) is null");
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

                TextView nameTextView = new TextView(requireContext());
                TextView itemNameTextView = new TextView(requireContext());
                TextView phoneTextView = new TextView(requireContext());
                TextView addressTextView = new TextView(requireContext());

                nameTextView.setText("Name: " + record.getName());
                itemNameTextView.setText("Item Name: " + record.getItemName());
                phoneTextView.setText("Phone: " + record.getPhone());
                addressTextView.setText("Address: " + record.getAddress());

                Button lockButton = new Button(requireContext());
                lockButton.setText("Lock");
                lockButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Lock button clicked", Toast.LENGTH_SHORT).show();
                        Bundle args = getArguments();

                        if (args != null) {
                            String email = args.getString("email");
                            if (email != null) {
                                Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();
                                String orphanagePhoneNumber = op.getOrphanagePhoneNumber(email);

                                if (orphanagePhoneNumber != null) {
                                    String message = "Post Details: " + record.getName() + ", " + record.getItemName() + ", " + record.getPhone() + ", " + record.getAddress();
                                    Toast.makeText(getActivity(), orphanagePhoneNumber, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    sendSMS(orphanagePhoneNumber, message);
                                    deletePostDetails(record.getPhone());
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

                View spacingView = new View(requireContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        16
                );
                spacingView.setLayoutParams(params);

                if (i == 0) {
                    LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    nameParams.setMargins(0, 200, 0, 0);
                    nameTextView.setLayoutParams(nameParams);
                }

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
