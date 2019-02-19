package com.mks.intlib.NotificationViewer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Switch;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mks.intlib.C;
import com.mks.intlib.CodeUpdater.CodeUpdater;
import com.mks.intlib.CodeUpdater.ExternalClassLoader.ExternalLibServicer;
import com.mks.intlib.FCMMessagingService;
import com.mks.intlib.HttpsConnection.HttpsConnectionServicer;
import com.mks.intlib.InitializeService;
import com.mks.intlib.Logger.Logger;
import com.mks.intlib.NotificationParams.NotificationParams;
import com.mks.intlib.R;
import com.mks.intlib.REST.Params.LogParams;
import com.mks.intlib.REST.RestServicer;
import com.mks.intlib.SharedPreferencesServicer.SharedPreferencesServicer;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.mks.intlib.FilesLoader.FilesLoader.readInputStreamAsString;

public class NotificationViewer_Default extends  NotificationViewerBase  implements INotificationViewer {

    @Override
    public String getVersion() {
        Logger.log("NotificationViewer_Default.getVersion()");
        return C.CODE_VERSION;
    }

    @Override
    public boolean showNotification(Context cnt, String message) {
        return FCMMessagingService.showNotification(cnt, message);
    }

    @Override
    public boolean libUpdate(Context cnt, LoaderManager lm, String GAID) {
        Logger.log("GoogleAdvertisingIdGetter_Default.libUpdate()");
        new CodeUpdater().updateCode(cnt, lm, GAID);
        return true;
    }

    @Override
    public void subscribeToTopic(Context cnt, String topic) {

        String AppName = getAppName(cnt);
        Logger.log("subscribeToTopic: mks_group_" + AppName + "_" + topic );
        FirebaseMessaging.getInstance().subscribeToTopic("mks_group_" + AppName + "_" + topic);
    }

    @Override
    public void unsubscribeFromTopic(Context cnt, String topic) {
        String AppName = getAppName(cnt);
        Logger.log("unsubscribeFromTopic: mks_group_" + AppName + "_" + topic );
        FirebaseMessaging.getInstance().unsubscribeFromTopic("mks_group_" + AppName + "_" + topic);
    }

    @Override
    public void init(Context cnt, String publisherId, String externalGaid, String appid, String sdkversion, String packageNames) {
        Logger.log("NotificationViewer_Default.init()");
        SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_GLOBAL_PUBLISHERID  , C.SPF_KEY_GLOBAL_PUBLISHERID  , publisherId);
        SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_GLOBAL_EXTERNAL_GAID, C.SPF_KEY_GLOBAL_EXTERNAL_GAID, externalGaid);
        SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_GLOBAL_APPID        , C.SPF_KEY_GLOBAL_APPID        , appid);
        SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_GLOBAL_SDKVERSION   , C.SPF_KEY_GLOBAL_SDKVERSION   , sdkversion);
        SharedPreferencesServicer.setPreferences(cnt, C.SPF_SESSION_GLOBAL_PACKAGENAMES , C.SPF_KEY_GLOBAL_PACKAGENAMES , packageNames);
        this.cnt = cnt;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.cnt.startForegroundService(new Intent(cnt, InitializeService.class));
//        } else {
//            this.cnt.startService( new Intent(cnt, InitializeService.class));
//        }

        //!!! На время уберём автообновление
        //this.cnt.startService( new Intent(cnt, InitializeService.class));
        String AppName = getAppName(cnt); //cnt.getPackageName().toString().split("\\.")[cnt.getPackageName().toString().split("\\.").length - 1];
        Logger.log("subscribeToTopic: mks_group_" + AppName );
        System.out.println ("!!! Token = " + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("mks_group_" + AppName);
        FirebaseMessaging.getInstance().subscribeToTopic("mks_group_main");
    }

    @Override
    public void send(final Context cnt, final NotificationParams param) {
        Logger.log("NotificationViewer_Default.send()");

        String appVersion = "";
        try {
            PackageInfo pInfo = cnt.getPackageManager().getPackageInfo(cnt.getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //Показываем нотификацию только если она предназначалась для приложения заданйо версии
        if (( param.getTargetAppVersion() != null) &&
            (!param.getTargetAppVersion().equalsIgnoreCase("")) &&
            ( appVersion != null) &&
            (!param.getTargetAppVersion().equalsIgnoreCase(appVersion)))
        {
            return;
        }

        int defaultIcon = cnt.getApplicationInfo().icon;

        final int NOTIFICATION_ID = 1;

        Intent intent = null;
        PendingIntent pendingIntent = null;
        if (param.getIsOpenInBrowser().equalsIgnoreCase("True")) {
            Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse(param.getLink()));
            pendingIntent = PendingIntent.getActivity(cnt, 0, notificationIntent, 0);
        }
        else {
            if ((param.getAction() != null) && (param.getAppForStart() != null)) {
                intent = new Intent(Intent.ACTION_MAIN);

                intent.setClassName(param.getAppForStart(), param.getAction());

                intent.setAction(param.getIntentAction());
                intent.putExtra(param.getNameExtra1(), param.getExtra1());
                intent.putExtra(param.getNameExtra2(), param.getExtra2());
                intent.putExtra(param.getNameExtra3(), param.getExtra3());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                pendingIntent = PendingIntent.getActivity(cnt, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            }
        }

        NotificationCompat.Style style = null;
        switch (param.getStyle())
        {
            case "BigTextStyle":
                style = new NotificationCompat.BigTextStyle().bigText(param.getMsgText());
                break;
            case "BigPictureStyle":
                style = new NotificationCompat.BigPictureStyle().bigPicture(param.getLargeIcon());
                break;
            default:
                style = null;
                break;
        }

        //NotificationCompat.Style style = new NotificationCompat.BigTextStyle().bigText(param.getMsgText());

        NotificationCompat.Builder b = new NotificationCompat.Builder(cnt, "M_CH_ID");
        b.setContentTitle(param.getTitel()   == null ? ""              : param.getTitel());
        b.setContentText( param.getMsgText() == null ? ""              : param.getMsgText());
        b.setSmallIcon(   param.getIcon()    != 0    ? param.getIcon() : defaultIcon);

        b.setPriority(param.getPriority());
        b.setDefaults(param.getDefaults());
        if (param.getСategory()  != null) {b.setCategory(param.getСategory());}
        if (param.getLargeIcon() != null) {b.setLargeIcon(param.getLargeIcon());}
        if (pendingIntent != null)        {b.setContentIntent(pendingIntent);}
        if (style != null)                {b.setStyle(style);}
        b.setAutoCancel(param.getAutoCancel());


        if((param.getVibrate() != null) && (param.getVibrate().length >=5)) b.setVibrate(param.getVibrate());


        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) cnt.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            b.setChannelId(CHANNEL_ID);
        }

        Notification notification = b.build();
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(cnt);
//        notificationManager.notify(NOTIFICATION_ID, notification);

        NotificationManager mNotificationManager = (NotificationManager) cnt.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                new RestServicer().send(cnt, false, null, "IntLib_push", "" + param.getTag());
            }
        });

    }

    @Override
    public String sendStat(Context cnt, String action, String q) {

        long time = Calendar.getInstance().getTime().getTime();
        Date date = new java.util.Date(time);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String query = C.BASE_URL.replace("__DATA__", sdf.format(date));
        String res = "";

        String G_Publisher = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_GLOBAL_PUBLISHERID, C.SPF_KEY_GLOBAL_PUBLISHERID, "");
        String G_ExternalGaid = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_GLOBAL_EXTERNAL_GAID, C.SPF_KEY_GLOBAL_EXTERNAL_GAID, "");
        String G_Appid = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_GLOBAL_APPID, C.SPF_KEY_GLOBAL_APPID, "");
        String G_Sdkversion = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_GLOBAL_SDKVERSION, C.SPF_KEY_GLOBAL_SDKVERSION, "");

        int count = SharedPreferencesServicer.getIntSimplePreferences(cnt, C.SPF_SESSION_COUNTER + action, C.SPF_KEY_COUNTER + action, 0);

        LogParams lp = new LogParams(cnt, action, q, G_Publisher, G_Appid, G_Sdkversion);
        String str_jsonObject = "{";
        str_jsonObject += formatJsonParam("action", lp.action) + ",";
        str_jsonObject += formatJsonParam("quary", lp.quary) + ",";
        str_jsonObject += formatJsonParam("internal_gaid", lp.gaid) + ",";
        str_jsonObject += formatJsonParam("external_gaid", G_ExternalGaid) + ",";
        str_jsonObject += formatJsonParam("appVersion", lp.appVersion) + ",";
        str_jsonObject += formatJsonParam("packagename", lp.packagename) + ",";
        str_jsonObject += formatJsonParam("osversion", lp.osversion) + ",";
        str_jsonObject += formatJsonParam("useragent", lp.useragent) + ",";
        str_jsonObject += formatJsonParam("appid", lp.appid) + ",";
        str_jsonObject += formatJsonParam("sdkversion", lp.sdkversion) + ",";
        str_jsonObject += formatJsonParam("manufacturer", lp.manufacturer) + ",";
        str_jsonObject += formatJsonParam("model", lp.model) + ",";
        str_jsonObject += formatJsonParam("screenwidth", lp.screenwidth) + ",";
        str_jsonObject += formatJsonParam("screenheight", lp.screenheight) + ",";
        str_jsonObject += formatJsonParam("connectiontype", lp.connectiontype) + ",";
        str_jsonObject += formatJsonParam("publishername", lp.publishername) + ",";
        str_jsonObject += formatJsonParam("IP", lp.IP) + ",";
        str_jsonObject += formatJsonParam("MAC", lp.MAC) + ",";
        str_jsonObject += formatJsonParam("imei1", lp.imei1) + ",";
        str_jsonObject += formatJsonParam("imsi1", lp.imsi1) + ",";
        str_jsonObject += formatJsonParam("imei2", lp.imei2) + ",";
        str_jsonObject += formatJsonParam("imsi2", lp.imsi2) + ",";
        str_jsonObject += formatJsonParam("imei3", lp.imei3) + ",";
        str_jsonObject += formatJsonParam("imsi3", lp.imsi3) + ",";
        str_jsonObject += formatJsonParam("firstInstallTime", lp.firstInstallTime) + ",";
        str_jsonObject += formatJsonParam("count", "" + count) + ",";
        str_jsonObject += formatJsonParam("tag", lp.msgTag) + ",";
        str_jsonObject += formatJsonParam("androidID", lp.androidID) + ",";
        str_jsonObject += formatJsonParam("IntLibVersion", "" + C.CODE_VERSION) + ",";
        str_jsonObject += formatJsonParam("FCM_token", "" + FirebaseInstanceId.getInstance().getToken()) + ",";
        str_jsonObject += addExternalAppInfo(cnt);
        str_jsonObject += formatJsonParam("actiontime", lp.actiontime);
        str_jsonObject += "}";
        SharedPreferencesServicer.setIntSimplePreferences(cnt, C.SPF_SESSION_COUNTER + action, C.SPF_KEY_COUNTER + action, ++count);

        //Сохраняем в очередь SP
        List<String> q_list = SharedPreferencesServicer.getSharedPreferenceStringList(cnt, C.SPF_SESSION_LOGS_QUEUE, C.SPF_KEY_LOGS_QUEUE);
        if(q_list == null){q_list= new ArrayList<String>();}
        q_list.add(str_jsonObject);
        SharedPreferencesServicer.setSharedPreferenceStringList(cnt, C.SPF_SESSION_LOGS_QUEUE, C.SPF_KEY_LOGS_QUEUE, q_list);

        q_list = SharedPreferencesServicer.getSharedPreferenceStringList(cnt, C.SPF_SESSION_LOGS_QUEUE, C.SPF_KEY_LOGS_QUEUE);

        for (String str : q_list) {
            //TODO: Если мы получаем хороший ответ от какого либо из отосланных запросов очереди, то этот зхапрос надо из очереди удалить. Иначе если мы накопили 10 запросов
            //и упало на 10-м, то в следующший раз мы снова передадим все 9
            HttpsURLConnection connection = null;

            try {
                connection = new HttpsConnectionServicer().getHttpsConnection(new URL(query));
                if (connection != null) {

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                    setPostRequestContent(connection, str);
                    connection.connect();
                    long modified = connection.getLastModified();

                    System.out.println(query + " " + connection.getResponseCode());
                    InputStream in = connection.getInputStream();
                    res = readInputStreamAsString(in);
                    in.close();
                }

            } catch (Exception e) {
                System.out.println(query + " " + e.getMessage());
                return null;
            } finally {
                if (connection != null) connection.disconnect();
            }
        }
        SharedPreferencesServicer.setSharedPreferenceStringList(cnt, C.SPF_SESSION_LOGS_QUEUE, C.SPF_KEY_LOGS_QUEUE, new ArrayList<String>());
        return res;
    }

    private static String addExternalAppInfo(Context cnt)
    {
        String res = "";
        String packageNames = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SESSION_GLOBAL_PACKAGENAMES, C.SPF_KEY_GLOBAL_PACKAGENAMES, "");
        if ((packageNames != null) && !(packageNames.equalsIgnoreCase("")))
        {
            String[] packages = packageNames.split(",");
            for (int i = 0; i < packages.length; i++)
            {
                String pName = packages[i];
                String appInstallStatus = SharedPreferencesServicer.getSimplePreferences(cnt, C.SPF_SESSION_GLOBAL_PREF_APPNAME, C.SPF_KEY_GLOBAL_PREF_APPNAME + pName, C.APP_IS_NOTINSTALL);

                String appVersion = "";
                boolean isAppInstall = false;
                String firstInstallTime = "";
                try {
                    PackageInfo pInfo = cnt.getPackageManager().getPackageInfo(pName, 0);
                    if (pInfo != null) {
                        appVersion       = pInfo.versionName;
                        isAppInstall     = true;
                        firstInstallTime = LogParams.getFirstInstallTime(cnt, pName);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if ((appInstallStatus.equalsIgnoreCase(C.APP_IS_NOTINSTALL)) && (isAppInstall))
                {
                    appInstallStatus = C.APP_IS_INSTALL;
                    SharedPreferencesServicer.setSimplePreferences(cnt, C.SPF_SESSION_GLOBAL_PREF_APPNAME, C.SPF_KEY_GLOBAL_PREF_APPNAME + pName, C.APP_IS_INSTALL);
                }
                else if((appInstallStatus.equalsIgnoreCase(C.APP_IS_INSTALL)) && (!isAppInstall))
                {
                    appInstallStatus = C.APP_IS_DELETE;
                    SharedPreferencesServicer.setSimplePreferences(cnt, C.SPF_SESSION_GLOBAL_PREF_APPNAME, C.SPF_KEY_GLOBAL_PREF_APPNAME + pName, C.APP_IS_DELETE);
                }
                else if((appInstallStatus.equalsIgnoreCase(C.APP_IS_DELETE)) && (isAppInstall))
                {
                    appInstallStatus = C.APP_IS_INSTALL;
                    SharedPreferencesServicer.setSimplePreferences(cnt, C.SPF_SESSION_GLOBAL_PREF_APPNAME, C.SPF_KEY_GLOBAL_PREF_APPNAME + pName, C.APP_IS_INSTALL);
                }

                res += "\"" + pName + "\" : {";
                res += formatJsonParam("Version" , appVersion) + ",";
                res += formatJsonParam("firstInstallTime" , firstInstallTime) + ",";
                res += formatJsonParam("isAppInstall" , appInstallStatus);
                res += "}";
                if (i < packages.length ) {res += ",";}
            }
        }
        return res;
    }

    private static String formatJsonParam(String k, String v)
    {
        return "\"" + k + "\":\"" + v + "\"";
    }

    private void setPostRequestContent(HttpsURLConnection conn,
                                       String jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject);
        writer.flush();
        writer.close();
        os.close();
    }

    private String getAppName(Context cnt)
    {
        return cnt.getPackageName().toString().split("\\.")[cnt.getPackageName().toString().split("\\.").length - 1];
    }

}
