package com.mks.intlib;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mks.intlib.CodeUpdater.ExternalClassLoader.ExternalLibServicer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/************************************************
 * Класс содержащий общие вспомогательные методы
* ***********************************************/
public class LibServicer {

    //Метод проверяет загружена ли "внешняя" DEX-часть
   public static boolean isExternalLibAccessible(Context cnt)
    {
        return (ExternalLibServicer.isExternalLibAccessible(cnt));
    }

    //Получение картинки по ссылке
    public static Bitmap getBitmapFromURL(String src) {
        if ((src == null)||(src.equals(""))){return null;}
        HttpURLConnection connection = null;
        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {connection.disconnect();}
        }
        return null;
    }

}
