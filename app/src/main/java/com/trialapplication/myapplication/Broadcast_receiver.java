package com.trialapplication.myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Broadcast_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new InternetCheck(context).execute();
    }

    public static class InternetCheck extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        InternetCheck(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            if (isConnected) {
                Toast.makeText(context, "Internet is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Internet is available", Toast.LENGTH_SHORT).show();
            }
        }
    }
}