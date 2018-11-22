package com.mks.intlib;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class RegistrationIntentService extends FirebaseInstanceIdService {
    // abbreviated tag name
    static final String TAG = "RegIntentService";
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
    }
}
