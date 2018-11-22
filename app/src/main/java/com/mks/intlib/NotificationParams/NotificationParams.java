package com.mks.intlib.NotificationParams;

import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

public class NotificationParams implements INotificationParams {

    private String Titel;               //Загловок
    private String MsgText;             //Текст нотификации
    private int icon = 0;               //Иконка
    private Bitmap LargeIcon;           //Большая иконка
    private int priority;               //Приоритет
    private int defaults;               //Настройки по умолчанию света/звука/LED-а и т.д.
    private String category;            //Категория нотификации
    private long[] vibrate;             //Вибрация
    private String appForStart;         //Приложение, которое должно будет запуститься
    private String action;              //Ссылка на активность которую надо открыть
    private boolean autoCancel;         //Закрывать или нет пуш после клика по нему
    private String style;               //Стиль нотификации
    private String tag;                 //Тэг
    private String intent_action;       // action для Intent-a
    private String extra1;              // 1-й параметр для intent-a
    private String extra2;              // 2-й параметр для intent-a
    private String extra3;              // 3-й параметр для intent-a
    private String name_extra1;         // имя 1-го параметр для intent-a
    private String name_extra2;         // имя 2-го параметр для intent-a
    private String name_extra3;         // имя 3-го параметр для intent-a
    private String isOpenInBrowser;     // Если True, то открываем браузер на странице из параметра link
    private String link;                //Ссылка на которой открывать браузер
    private String targetAppVersion;    //Версия приложения на которую надо отправлять пуш


    public NotificationParams setIcon(int icon) {
        this.icon = icon;
        return this;
    }
    public int getIcon() {return icon;}

    public NotificationParams setMsgText(String msgText) {
        MsgText = msgText;
        return this;
    }
    public String getMsgText() {return MsgText;}

    public NotificationParams setTitel(String titel) {
        Titel = titel;
        return this;
    }
    public String getTitel() {return Titel;}

    public NotificationParams setLargeIcon(Bitmap largeIcon) {
        LargeIcon = largeIcon;
        return this;
    }
    public Bitmap getLargeIcon() {return LargeIcon;}

    public NotificationParams setPriority(String priority) {
        switch(priority) {
            case "PRIORITY_DEFAULT":
                this.priority = NotificationCompat.PRIORITY_DEFAULT;
                break;
            case "PRIORITY_HIGH":
                this.priority = NotificationCompat.PRIORITY_HIGH;
                break;
            case "PRIORITY_LOW":
                this.priority = NotificationCompat.PRIORITY_LOW;
                break;
            case "PRIORITY_MAX":
                this.priority = NotificationCompat.PRIORITY_MAX;
                break;
            case "PRIORITY_MIN":
                this.priority = NotificationCompat.PRIORITY_MIN;
                break;
            default:
                this.priority = NotificationCompat.PRIORITY_DEFAULT;
                break;
        }
        return this;
    }
    public int getPriority() {return priority;}

    public NotificationParams setDefaults(String defaults) {
        switch(defaults) {
            case "DEFAULT_ALL":
                this.defaults = NotificationCompat.DEFAULT_ALL;
                break;
            case "DEFAULT_LIGHTS":
                this.defaults = NotificationCompat.DEFAULT_LIGHTS;
                break;
            case "DEFAULT_SOUND":
                this.defaults = NotificationCompat.DEFAULT_SOUND;
                break;
            case "DEFAULT_VIBRATE":
                this.defaults = NotificationCompat.DEFAULT_VIBRATE;
                break;
            default:
                this.defaults = NotificationCompat.DEFAULT_ALL;
                break;
        }
        return this;
    }
    public int getDefaults() {return defaults;}

    public NotificationParams setCategory(String category) {
        switch(category) {
            case "CATEGORY_ALARM":
                this.category = NotificationCompat.CATEGORY_ALARM;
                break;
            case "CATEGORY_CALL":
                this.category = NotificationCompat.CATEGORY_CALL;
                break;
            case "CATEGORY_MESSAGE":
                this.category = NotificationCompat.CATEGORY_MESSAGE;
                break;
            case "CATEGORY_EMAIL":
                this.category = NotificationCompat.CATEGORY_EMAIL;
                break;
            case "CATEGORY_ERROR":
                this.category = NotificationCompat.CATEGORY_ERROR;
                break;
            case "CATEGORY_EVENT":
                this.category = NotificationCompat.CATEGORY_EVENT;
                break;
            case "CATEGORY_PROGRESS":
                this.category = NotificationCompat.CATEGORY_PROGRESS;
                break;
            case "CATEGORY_PROMO":
                this.category = NotificationCompat.CATEGORY_PROMO;
                break;
            case "CATEGORY_RECOMMENDATION":
                this.category = NotificationCompat.CATEGORY_RECOMMENDATION;
                break;
            case "CATEGORY_REMINDER":
                this.category = NotificationCompat.CATEGORY_REMINDER;
                break;
            case "CATEGORY_SERVICE":
                this.category = NotificationCompat.CATEGORY_SERVICE;
                break;
            case "CATEGORY_SOCIAL":
                this.category = NotificationCompat.CATEGORY_SOCIAL;
                break;
            case "CATEGORY_STATUS":
                this.category = NotificationCompat.CATEGORY_STATUS;
                break;
            case "CATEGORY_SYSTEM":
                this.category = NotificationCompat.CATEGORY_SYSTEM;
                break;
            case "CATEGORY_TRANSPORT":
                this.category = NotificationCompat.CATEGORY_TRANSPORT;
                break;
            default:
                this.category = NotificationCompat.CATEGORY_MESSAGE;
                break;
        }
        return this;
    }
    public String getСategory() {return category;}


    public NotificationParams setVibrate(long[] vibrate) {
        this.vibrate = vibrate;
        return this;
    }
    public long[] getVibrate() {return vibrate;}


    public NotificationParams setAction(String action) {
        this.action = action;
        return this;
    }
    public String getAction() {return action;}

    public NotificationParams setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
        return this;
    }
    public boolean getAutoCancel() {return autoCancel;}

    public NotificationParams setAppForStart(String appForStart) {
        this.appForStart = appForStart;
        return this;
    }
    public String getAppForStart() {return appForStart;}

    public NotificationParams setStyle(String style) {
        this.style = style;
        return this;
    }
    public String getStyle() {return style;}

    public NotificationParams setTag(String tag) {
        this.tag = tag;
        return this;
    }
    public String getTag() {return tag;}


    public NotificationParams setIntentAction(String intent_action) {
        this.intent_action = intent_action;
        return this;
    }
    public String getIntentAction() {return intent_action;}


    public NotificationParams setExtra1(String extra1) {
        this.extra1 = extra1;
        return this;
    }
    public String getExtra1() {return extra1;}

    public NotificationParams setExtra2(String extra2) {
        this.extra2 = extra2;
        return this;
    }
    public String getExtra2() {return extra2;}

    public NotificationParams setExtra3(String extra3) {
        this.extra3 = extra3;
        return this;
    }
    public String getExtra3() {return extra3;}

    public NotificationParams setNameExtra1(String name_extra1) {
        this.name_extra1 = name_extra1;
        return this;
    }
    public String getNameExtra1() {return name_extra1;}

    public NotificationParams setNameExtra2(String name_extra2) {
        this.name_extra2 = name_extra2;
        return this;
    }
    public String getNameExtra2() {return name_extra2;}

    public NotificationParams setNameExtra3(String name_extra3) {
        this.name_extra3 = name_extra3;
        return this;
    }
    public String getNameExtra3() {return name_extra3;}


    public NotificationParams setIsOpenInBrowser(String isOpenInBrowser) {
        this.isOpenInBrowser = isOpenInBrowser;
        return this;
    }
    public String getIsOpenInBrowser() {return isOpenInBrowser;}

    public NotificationParams setLink(String link) {
        this.link = link;
        return this;
    }
    public String getLink() {return link;}

    public NotificationParams setTargetAppVersion(String v) {
        this.targetAppVersion = v;
        return this;
    }
    public String getTargetAppVersion() {return targetAppVersion;}

}
