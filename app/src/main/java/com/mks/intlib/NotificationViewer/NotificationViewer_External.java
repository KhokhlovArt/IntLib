package com.mks.intlib.NotificationViewer;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.util.Log;

import com.mks.intlib.C;
import com.mks.intlib.CodeUpdater.ExternalClassLoader.ExternalLibServicer;
import com.mks.intlib.Logger.Logger;
import com.mks.intlib.NotificationParams.NotificationParams;

public class NotificationViewer_External extends  NotificationViewerBase  implements INotificationViewer {

    @Override
    public void init(Context cnt, String publisherId, String externalGaid, String appid, String sdkversion, String packageNames) {
        Logger.log("NotificationViewer_External.init()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});

        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default     = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "init", new Object[]{cnt, publisherId, externalGaid, appid, sdkversion}, new Class[]{Context.class, String.class, String.class, String.class, String.class});
    }

    @Override
    public void send(Context cnt, NotificationParams param) {
        Logger.log("NotificationViewer_External.send()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});

        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default     = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        Class clazzNotificationParams  = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationParams.NotificationParams");

        loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "send", new Object[]{cnt, param}, new Class[]{Context.class, clazzNotificationParams});
    }

    @Override
    public String getVersion() {
        Logger.log("NotificationViewer_External.getVersion()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});


        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        String str                       = loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "getVersion", new Object[]{}, new Class[]{});
        return str;
    }

    @Override
    public boolean showNotification(Context cnt, String message) {
        Logger.log("NotificationViewer_External.showNotification()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});

        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "showNotification", new Object[]{cnt, message}, new Class[]{Context.class, String.class});
        return true;
    }

    @Override
    public boolean libUpdate(Context cnt, LoaderManager lm, String GAID) {
        Logger.log("NotificationViewer_External.libUpdate()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});

        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default     = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});
        loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "libUpdate", new Object[]{cnt, lm, GAID}, new Class[]{Context.class, LoaderManager.class, String.class});
        return true;
    }

    @Override
    public void subscribeToTopic(Context cnt, String topic) {
        Logger.log("NotificationViewer_External.subscribeToTopic()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});

        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default     = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "subscribeToTopic", new Object[]{cnt, topic}, new Class[]{Context.class, String.class});
    }

    @Override
    public void unsubscribeFromTopic(Context cnt, String topic) {
        Logger.log("NotificationViewer_External.unsubscribeFromTopic()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});

        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default     = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "unsubscribeFromTopic", new Object[]{cnt, topic}, new Class[]{Context.class, String.class});
    }

    @Override
    public String sendStat(Context cnt, String action, String quary) {
        Logger.log("NotificationViewer_External.sendStat()");
        ExternalLibServicer loader = ExternalLibServicer.getServicer(cnt);
        Class clazzNotificationViewerGetter = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewerGetter");
        Object instance                     = loader.getInstance(clazzNotificationViewerGetter, new Object[]{}, new Class[]{});


        Class clazzNotificationViewer_Default = loader.getExternalClass(cnt, C.EXTERNAL_PACKAGE_NAME + ".NotificationViewer.NotificationViewer_Default");
        Object NotificationViewer_Default = loader.callMethod(clazzNotificationViewerGetter, instance, "getNotificationViewer", new Object[]{cnt}, new Class[]{Context.class});

        String str                       = loader.callMethod(clazzNotificationViewer_Default, NotificationViewer_Default, "sendStat",
                new Object[]{cnt, action, quary},
                new Class[]{Context.class, String.class, String.class,String.class,String.class,String.class});
        return str;
    }
}
