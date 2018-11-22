package com.mks.intlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.mks.intlib.Logger.Logger;
import com.mks.intlib.NotificationViewer.INotificationViewer;
import com.mks.intlib.NotificationViewer.NotificationViewer_Default;
import com.mks.intlib.NotificationViewer.NotificationViewer_External;

public class NotificationViewerGetter{

    public INotificationViewer getNotificationViewer(Context cnt) {
        Logger.log("NotificationViewerGetter.getNotificationViewer()");
        if (LibServicer.isExternalLibAccessible(cnt))
        {
            return new NotificationViewer_External();
        }
        else
        {
            return new NotificationViewer_Default();
        }
    }
}
