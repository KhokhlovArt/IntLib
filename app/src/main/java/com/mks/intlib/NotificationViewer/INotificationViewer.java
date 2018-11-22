package com.mks.intlib.NotificationViewer;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.mks.intlib.NotificationParams.NotificationParams;

public interface INotificationViewer {
    void init(Context cnt, String publisherId, String externalGaid, String appid, String sdkversion, String packageNames);
    void send(Context cnt, NotificationParams param);
    String getVersion();
    boolean showNotification(Context cnt, String message);
    boolean libUpdate(Context cnt, LoaderManager lm, String GAID);
    void subscribeToTopic(Context cnt, String topic);
    void unsubscribeFromTopic(Context cnt, String topic);
    String sendStat(Context cnt, String action, String query);
}
