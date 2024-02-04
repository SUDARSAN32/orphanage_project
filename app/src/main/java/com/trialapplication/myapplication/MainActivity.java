package com.trialapplication.myapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    Button b1;
    TextView b2;
    private Broadcast_receiver networkChangeReceiver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editText1);
        e2 = findViewById(R.id.editText2);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.textView1);

        networkChangeReceiver = new Broadcast_receiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Handle network change events if needed
                showToast("Network state changed");
            }
        };
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = e1.getText().toString();
                String password = e2.getText().toString();

                if (username.equals("")) {
                    e1.setError("Email is required");
                    return;
                }

                if (password.equals("")) {
                    e2.setError("Password is required");
                    return;
                }

                UserHelperClass userHelper = new UserHelperClass(MainActivity.this);
                boolean isUsauth = userHelper.authenticateUser(username, password);

                orphanageHelper orphanHelper = new orphanageHelper(MainActivity.this);
                boolean isOpauth = orphanHelper.authenticateOrphanage(username, password);

                if (isUsauth) {
                    Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(intent);
                    finish();
                }
                else if(isOpauth) {

                    Bundle bundle = new Bundle();
                    bundle.putString("email1", username);
                    view_fragment_orphanage orphanageFragment = new view_fragment_orphanage();
                    orphanageFragment.setArguments(bundle);

                    Intent intent = new Intent(MainActivity.this,MainActivity_orphanage.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
