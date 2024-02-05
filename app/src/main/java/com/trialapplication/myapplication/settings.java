package com.trialapplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class settings extends AppCompatActivity {
    fragiler f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        f1 = new fragiler(this);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            String z = args.getString("email");
            Cursor c1 = f1.getrecord(z);


            if (c1 != null && c1.moveToFirst()) {
                do {
                    TextView nameTextView = new TextView(this);
                    TextView itemNameTextView = new TextView(this);
                    TextView phoneTextView = new TextView(this);
                    TextView addressTextView = new TextView(this);

                    int nameIndex = c1.getColumnIndex(f1.col1);
                    int itemNameIndex = c1.getColumnIndex(f1.col2);
                    int phoneIndex = c1.getColumnIndex(f1.col3);
                    int addressIndex = c1.getColumnIndex(f1.col4);
                    if (nameIndex >= 0) {
                        nameTextView.setText("Name: " + c1.getString(nameIndex));
                    }

                    if (itemNameIndex >= 0) {
                        itemNameTextView.setText("Item Name: " + c1.getString(itemNameIndex));
                    }

                    if (phoneIndex >= 0) {
                        phoneTextView.setText("Phone: " + c1.getString(phoneIndex));
                    }

                    if (addressIndex >= 0) {
                        addressTextView.setText("Address: " + c1.getString(addressIndex));
                    }

                    linearLayout.addView(nameTextView);
                    linearLayout.addView(itemNameTextView);
                    linearLayout.addView(phoneTextView);
                    linearLayout.addView(addressTextView);
                } while (c1.moveToNext());
            }

            if (c1 != null) {
                c1.close();
            }
        }
    }

}
