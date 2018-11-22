package com.mks.intlib.REST;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.mks.intlib.LibServicer;
import com.mks.intlib.NotificationViewer.NotificationViewer_Default;
import com.mks.intlib.NotificationViewer.NotificationViewer_External;

public class RestServicer {
    public static final int LOADER_SEND_LOG = 1;
    public String send(final Context cnt, final boolean isAsincStart, final LoaderManager lm , final String action, final String query)
    {
        if (isAsincStart) {
            LoaderManager.LoaderCallbacks<String> tmp = new LoaderManager.LoaderCallbacks<String>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public Loader<String> onCreateLoader(int i, Bundle bundle) {
                    return new AsyncTaskLoader<String>(cnt) {
                        public String loadInBackground() {
                            if (LibServicer.isExternalLibAccessible(cnt)) { //Если выполняем из внешней библиотеки
                                return new NotificationViewer_External().sendStat(cnt, action , query);
                            } else {
                                return new NotificationViewer_Default().sendStat(cnt, action , query);
                            }
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<String> loader, String result) {
//                    if (param.getOnResultListener() == null) return;
//                    param.getOnResultListener().onResult((result == null ? RestServicer.CODE_NULL_RESULT : RestServicer.CODE_OK), result);
                }

                @Override
                public void onLoaderReset(Loader<String> loader) {
                }
            };
            Loader<String> l = lm.restartLoader(RestServicer.LOADER_SEND_LOG, null, tmp);
            l.forceLoad();
            return null;
        }
        else {
            if (LibServicer.isExternalLibAccessible(cnt)) { //Если выполняем из внешней библиотеки
                return new NotificationViewer_External().sendStat(cnt, action , query);
            } else {
                return new NotificationViewer_Default().sendStat(cnt, action , query);
            }
        }

    }
}
