package com.mks.intlib.NotificationViewer;

import android.content.Context;

import com.mks.intlib.Logger.Logger;

public class NotificationViewer_Default implements INotificationViewer {
    @Override
    public void send(Context cnt) {
        Logger.log("NotificationViewer_Default.send()");
    }
}
