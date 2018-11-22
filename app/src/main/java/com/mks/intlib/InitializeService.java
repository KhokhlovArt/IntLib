package com.mks.intlib;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.mks.intlib.Logger.Logger;
import com.mks.intlib.SharedPreferencesServicer.SharedPreferencesServicer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class InitializeService  extends Service {
    static int ONE_DAY = 24*60*60*1000;
    static Boolean isServiceStart = false;
    Date currentTime;


    long getPeriod()
    {
        String period = SharedPreferencesServicer.getSimplePreferences(getApplicationContext(), C.SPF_SESSION_PERIOD, C.SPF_KEY_PERIOD, "");
        if((period == null) || (period.equals("")))
        {
            return 4*60*60*1000;
        }
        else
        {
            return Long.parseLong(period);
        }
    }

    long generateShift()
    {
        Calendar now = Calendar.getInstance();
        Random r = new Random();
        String seed = "" + now.toString();
        r.setSeed(seed.hashCode());
        return r.nextInt(ONE_DAY);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String nextStartTime = SharedPreferencesServicer.getPreferences(getApplicationContext(), C.SESSION_NEXT_START_TIME, C.KEY_NEXT_START_TIME, "");
            if ((nextStartTime == null) || (nextStartTime.equals(""))) {
                nextStartTime = "0";
            }
            Date date = new java.util.Date(Long.parseLong(nextStartTime));
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("nextStartTime = " + nextStartTime + "[" + sdf.format(date) + "]");
        }
        catch (Error err)
        {
            System.out.println("nextStartTime = ??? : " + err);
        }

        if (isServiceStart == true) {return  super.onStartCommand(intent, flags, startId);}
        isServiceStart = true;



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                currentTime = Calendar.getInstance().getTime();

                long lastStartTime = 0;
                String savedLastStartTime = SharedPreferencesServicer.getPreferences(getApplicationContext(), C.SESSION_LAST_START_TIME, C.KEY_LAST_START_TIME, "");
                if ((savedLastStartTime != null) && (!savedLastStartTime.equals(""))) {
                    lastStartTime = Long.parseLong(savedLastStartTime);
                }else{
                    lastStartTime = Calendar.getInstance().getTime().getTime();
                    SharedPreferencesServicer.setPreferences(getApplicationContext(), C.SESSION_LAST_START_TIME, C.KEY_LAST_START_TIME, "" + lastStartTime);
                }

                long nextStartTime = 0;
                String savedNextStartTime = SharedPreferencesServicer.getPreferences(getApplicationContext(), C.SESSION_NEXT_START_TIME, C.KEY_NEXT_START_TIME, "");
                if ((savedNextStartTime != null) && (!savedNextStartTime.equals(""))) {
                    nextStartTime = Long.parseLong(savedNextStartTime);
                }else{
                    nextStartTime = Calendar.getInstance().getTime().getTime() + generateShift();
                    SharedPreferencesServicer.setPreferences(getApplicationContext(), C.SESSION_NEXT_START_TIME, C.KEY_NEXT_START_TIME, "" + nextStartTime);
                }

//                Log.e("12345", "v 2.X.X timer: time " + (currentTime.getTime() - nextStartTime) + " || " + (currentTime.getTime() - lastStartTime) +">" + ONE_DAY + "["+ (currentTime.getTime() - lastStartTime > ONE_DAY) +"]"  );

                if ((currentTime.getTime() > nextStartTime) || (Math.abs(currentTime.getTime() - lastStartTime) > ONE_DAY) )
                {
                    lastStartTime = Calendar.getInstance().getTime().getTime();
                    SharedPreferencesServicer.setPreferences(getApplicationContext(), C.SESSION_LAST_START_TIME, C.KEY_LAST_START_TIME, "" + lastStartTime);

                    nextStartTime = currentTime.getTime() + getPeriod();
                    SharedPreferencesServicer.setPreferences(getApplicationContext(), C.SESSION_NEXT_START_TIME, C.KEY_NEXT_START_TIME, "" + nextStartTime);

                    String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    new NotificationViewerGetter().getNotificationViewer(getApplicationContext()).libUpdate(getApplicationContext(), null, android_id);
                    Log.d("!!!","Start lib update");
                }
            }
        }, 0, 20000);

        return super.onStartCommand(intent, flags, startId);
    }
}
