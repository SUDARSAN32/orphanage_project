package com.trialapplication.myapplication;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.Arrays;
import java.util.List;


public class MyIntentservice extends IntentService {

    private static final String ACTION_NUMBER_VALIDATION = "com.trialapplication.myapplication.action.NUMBER_VALIDATION";
    private static final String Orphanage_id = "com.trialapplication.myapplication.extra.PHONE_NUMBER";

    public MyIntentservice() {
        super("MyIntentService");
    }

    public static void startActionNumberValidation(Context context, String phoneNumber) {
        Intent intent = new Intent(context, MyIntentservice.class);
        intent.setAction(ACTION_NUMBER_VALIDATION);
        intent.putExtra(Orphanage_id, phoneNumber);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NUMBER_VALIDATION.equals(action)) {
                final String phoneNumber = intent.getStringExtra(Orphanage_id);
                handleActionNumberValidation(phoneNumber);
            }
        }
    }

    private void handleActionNumberValidation(String phoneNumber) {
        List<String> validNumbers = Arrays.asList("421", "315", "13981", "59");
        boolean isValid = validNumbers.contains(phoneNumber);
        sendResultToCaller(isValid);
    }

    private void sendResultToCaller(boolean isValid) {
        Intent broadcastIntent = new Intent("NumberValidationResult");
        broadcastIntent.putExtra("isValid", isValid);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }


}
