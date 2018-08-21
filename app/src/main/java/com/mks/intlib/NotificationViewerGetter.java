package com.mks.intlib;

import android.content.Context;

import com.mks.intlib.Logger.Logger;
import com.mks.intlib.NotificationViewer.INotificationViewer;
import com.mks.intlib.NotificationViewer.NotificationViewer_Default;
import com.mks.intlib.NotificationViewer.NotificationViewer_External;

public class NotificationViewerGetter implements INotificationViewer{
    @Override
    public void send(Context cnt) {
        Logger.log("NotificationViewerGetter.send()");
        if (LibServicer.isExternalLibAccessible(cnt))
        {
            new NotificationViewer_External().send(cnt);
        }
        else
        {
            new NotificationViewer_Default().send(cnt);
        }
    }
}
