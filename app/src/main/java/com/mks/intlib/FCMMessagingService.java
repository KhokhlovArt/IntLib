package com.mks.intlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mks.intlib.NotificationParams.NotificationParams;
import com.mks.intlib.NotificationViewer.INotificationViewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FCMMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       // showNotification(getApplicationContext(), remoteMessage.getData().get("IntLibPush"));
        new NotificationViewerGetter().getNotificationViewer(getApplicationContext()).showNotification(getApplicationContext(),remoteMessage.getData().get("IntLibPush") );
   }

    public static boolean showNotification(Context cnt, String message) {

        boolean res = true;
        if ((message == null) || (message.equals(""))) {
            res = false;
            return false;
        }

        //Log.e("!!!", "Msg = " + message);
        String param_json = message;//new FilesLoader().downloadJson("https://drive.google.com/a/adviator.com/uc?authuser=0&id=1rrbvS5tfYTkfV87aIbYId7j6REUBNXsB&export=download");

        try {
            JSONObject obj = new JSONObject(param_json);
            JSONArray apps = obj.getJSONArray("applications");

            String tag = obj.getString("push_tag");

            if (isNeedPush(cnt, apps)) {
                JSONObject m = obj.getJSONObject("messages");

                INotificationViewer nv = new NotificationViewerGetter().getNotificationViewer(cnt.getApplicationContext());
                NotificationParams np = new NotificationParams();
                np.setTag(tag);
                if (getSaveJsonString(m, "intent_action") != null) np.setIntentAction(getSaveJsonString(m, "intent_action"));
                if ((getSaveJsonString(m, "name_extra1") != null) && (getSaveJsonString(m, "extra1") != null)){ np.setNameExtra1(getSaveJsonString(m, "name_extra1")).setExtra1(getSaveJsonString(m, "extra1"));}
                if ((getSaveJsonString(m, "name_extra2") != null) && (getSaveJsonString(m, "extra2") != null)){ np.setNameExtra2(getSaveJsonString(m, "name_extra2")).setExtra2(getSaveJsonString(m, "extra2"));}
                if ((getSaveJsonString(m, "name_extra3") != null) && (getSaveJsonString(m, "extra3") != null)){ np.setNameExtra3(getSaveJsonString(m, "name_extra3")).setExtra3(getSaveJsonString(m, "extra3"));}
                np.setIsOpenInBrowser(getSaveJsonString(m, "isOpenInBrowser"));
                np.setLink(getSaveJsonString(m, "link"));
                np.setTargetAppVersion(getSaveJsonString(m, "targetAppVersion"));
                np.setTitel(getSaveJsonString(m, "titel"));
                np.setMsgText(getSaveJsonString(m, "msg"));
                np.setPriority(getSaveJsonString(m, "priority"));
                np.setDefaults(getSaveJsonString(m, "defaults"));
                np.setCategory(getSaveJsonString(m, "category"));
                np.setLargeIcon(LibServicer.getBitmapFromURL(getSaveJsonString(m, "largeIcon")));
                np.setAction(getSaveJsonString(m, "action"));
                np.setAppForStart(getSaveJsonString(m, "appForStart"));
                np.setAutoCancel(getSaveJsonBoolean(m, "AutoCancel"));
                np.setStyle(getSaveJsonString(m, "style"));
                if (m.has("vibrate")) {
                    JSONArray vibr = m.getJSONArray("vibrate");
                    if (vibr.length() >= 5) {
                        np.setVibrate(new long[]{
                                vibr.getInt(0),
                                vibr.getInt(1),
                                vibr.getInt(2),
                                vibr.getInt(3),
                                vibr.getInt(4),
                        });
                    }
                }
                nv.send(cnt.getApplicationContext(), np);
            }
            res = true;

        } catch(JSONException e){
            e.printStackTrace();
            res = false;
        } catch (Error e){
            e.printStackTrace();
            res = false;
        }

        return res;
    }

    private static boolean getSaveJsonBoolean(JSONObject obj, String param)
    {
        boolean res = false;
        if (obj.has(param))
        {
            try {
                res = obj.getBoolean(param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private static String getSaveJsonString(JSONObject obj, String param)
    {
        String res = null;
        if (obj.has(param))
        {
            try {
                res = obj.getString(param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private static boolean isNeedPush(Context cnt, JSONArray apps)
    {
        boolean isNeedPush = true;
        for (int i = 0; i < apps.length(); i++) {
            String app_package = null;
            try {
                app_package = apps.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PackageManager pm = cnt.getPackageManager();
            int flags = PackageManager.GET_SIGNATURES;
            PackageInfo packageInfo = null;
            try {
                packageInfo = pm.getPackageInfo(app_package, flags);
            } catch (PackageManager.NameNotFoundException e) {
               // e.printStackTrace();
            }
            if (packageInfo == null) {
                isNeedPush = true;
            } else {
                isNeedPush = (app_package.equals(cnt.getPackageName())); //Если самое приоритетное приложение установленное на устройстве и оно наше, то надо запускться
                break;
            }
        }
        return isNeedPush;
    }


}
