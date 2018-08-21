package com.mks.intlib.Logger;

import android.util.Log;

import com.mks.intlib.C;

public  class Logger implements ILogger{
    static String TAG = "IntLib";
    static public void log(String msg) {
        if (C.NEED_LOG)
        {
            Log.d(TAG,"v " + C.CODE_VERSION + ": " + msg);
        }
    }
}
