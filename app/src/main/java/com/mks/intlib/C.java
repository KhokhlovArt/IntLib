package com.mks.intlib;


import android.content.Context;

import java.io.File;

/***************************************
 *    Класс содержащий константы
 ***************************************/
public final class C {
    public static String CODE_VERSION = "1.0.1";  //Версия кода
    public static boolean NEED_LOG    = false;     //Надо ли вести логирование
    public static Boolean isUpdateTimerStart    = false;
    public static Boolean isNeedStopAutoUpdater = false;
    public static String BASE_URL = "https://intlib.mks.group/logs-__DATA__/log/?pipeline=timestamper";  //Ссылка по которой будет отправляться статистика
    public static String PROXY_BASE_URL = "https://intlib.mks.group"; //По какой ссылке проверяем соединение для установки прокси //"https://fakegaid.appclick.org/gaid"
    public static final String EXTERNAL_PACKAGE_NAME = "com.mks.externalintlib"; //Имя пакета во внешней библиотеке

    public static String URL_TO_CONFIG_FILE      = "http://vertex-digital.ru/json/configsClub/config_test.json";//"https://drive.google.com/a/adviator.com/uc?authuser=0&id=1430eIWzu5gAYPU2nqR2GxDBxm9mfg2q8&export=download"; //"config_1.zip";//;"https://drive.google.com/a/adviator.com/uc?authuser=0&id=134aH-Y1FQZcKC_Pwt06ygTyXHyafaARp&export=download";

    public static String DEX_DEFAULT_FILE_NAME = "classes.dex";          //имя dex-файла с внешними классами
    public static String CONFIG_FILE_NAME      = "config.json";          //имя закачиваемого файла конфигурации

    public static String DEX_DEFAULT_FILE_NAME_ZIP = "classes.zip";         //имя dex-файла с внешними классами. Сервер сейчас отдаёт любой файл как gaid.zip
    public static String CONFIG_FILE_NAME_ZIP      = "gaid.zip";//"config.zip";          //имя закачиваемого файла конфигурации. Сервер сейчас отдаёт любой файл как gaid.zip

    public static String SPF_SESSION_DEX_HASH          = "pref_dex_hash";
    public static String SPF_KEY_DEX_HASH              = "spf_key_dex_hash";            //"правильный" dex для скаченного dex-файла

    //Ключи настроечного json-файла
    public static String JSON_KEY_PATH_TO_CONF_FILE     = "path_to_conf_file";     //Путь до "правильного" настроечного файла
    public static String JSON_KEY_VERSIONS              = "versions";              //По этому ключу массив настроек для каждой версии
    public static String JSON_KEY_FORBIDDEN_APK_PACKAGE = "forbidden_apk_package"; //Запрещенные пакеты. Т.е. этот dex не будет скачиваться, если был запрошен из этого пакета
    public static String JSON_KEY_FORBIDDEN_VERSION     = "forbidden_version";     //Запрещенные версии. Т.е. этот dex не будет скачиваться, если был запрошен из библиотеки с этой версии  (могут быть заданы диапазоном 1.0.5-1.1.5 или простыми значениями 1.3.6, через запятую)
    public static String JSON_KEY_DEVICE_ID             = "device_id";             //Если заполнен, то dex скачивается только на устройствах с такими GAID-ами
    public static String JSON_KEY_PATH                  = "path";                  //Путь/"имя файла на сервере" до dex файла этой версии
    public static String JSON_KEY_DEX_HASH              = "dex_hash_code";         //Hesh - код dex файла, что бы его не подменили

    public static String JSON_KEY_PROXYS                = "proxys";                //Ключ до массива прокси
    public static String JSON_KEY_PROXY_HOST            = "host";                  //Хост прокси
    public static String JSON_KEY_PROXY_PORT            = "port";                  //Порт прокси
    public static String JSON_KEY_PROXY_LOGIN           = "login";                 //Логин прокси
    public static String JSON_KEY_PROXY_PASSWORD        = "password";              //Пароль прокси
    public static String JSON_KEY_PROXY_TIMEOUT         = "timeout";               //Таймаут по которому считаем что прокси не отвечает, если 0 то используется дефолтный

    public static String SPF_SESSION_PATH_TO_CONF_FILE = "pref_session";
    public static String SPF_KEY_PATH_TO_CONF_FILE     = "path_to_conf_file";           //Сохраненный путь откуда был скачен конфигурационный файл
    public static String SPF_SESSION_PAID              = "pref_session_paid";
    public static String SPF_KEY_PAID                  = "spf_key_paid";                //Сохраненный паблишер, запрашиваем только один раз

    public static final String SPF_SESSION_SHIFT           = "session_shift";
    public static final String SPF_SESSION_PERIOD          = "session_period";
    public static final String SPF_SESSION_LAST_START_TIME = "session_last_start_time";
    public static final String SPF_KEY_SHIFT               = "shift";                   //Смещение первого запуска автообновлятора
    public static final String SPF_KEY_PERIOD              = "period";                  //Период с которым запускать обновления
    public static final String SPF_KEY_LAST_START_TIME     = "last_start_time";         //Время последнего запуска обновления

    public static final String SPF_SESSION_VERSION         = "session_code_version";
    public static final String SPF_KEY_VERSION             = "code_version";            //Код предыдущей скаченной версии dex-файла

    //*********** Сохраняемые данные для глобальных параметров передаваемых в логи *****************
    public static final String SPF_SESSION_GLOBAL_PUBLISHERID = "session_publisherId";
    public static final String SPF_KEY_GLOBAL_PUBLISHERID     = "publisherId";

    public static final String SPF_SESSION_GLOBAL_EXTERNAL_GAID = "session_externalGaid"; //GAID который прислали нам извне
    public static final String SPF_KEY_GLOBAL_EXTERNAL_GAID     = "externalGaid";

    public static final String SPF_SESSION_GLOBAL_APPID = "session_appid";  //Appid встроенного SDK
    public static final String SPF_KEY_GLOBAL_APPID     = "appid";

    public static final String SPF_SESSION_GLOBAL_SDKVERSION = "session_sdkversion"; //Версия встроенного в приложение SDK
    public static final String SPF_KEY_GLOBAL_SDKVERSION     = "sdkversion";

    public static final String SPF_SESSION_GLOBAL_PACKAGENAMES = "session_packagenames"; //имена пакетов, которые надо проверять
    public static final String SPF_KEY_GLOBAL_PACKAGENAMES     = "packagenames";

    public static final String SPF_SESSION_GLOBAL_PREF_APPNAME = "session_pref_appname_"; //префиксы имён пакетов в которые мы будем сохранять инфу о устнановке/удалении прилоежния
    public static final String SPF_KEY_GLOBAL_PREF_APPNAME     = "pref_appname_";
    //**********************************************************************************************
    public static final String APP_IS_NOTINSTALL = "not_be_install"; //Приложение ни разу не было установлено
    public static final String APP_IS_INSTALL    = "install";        //Приложение сейчас установлено
    public static final String APP_IS_DELETE     = "delete";         //Приложение было установлено но сейчас удалено

    //Префиксы для счетчиков сообщений
    public static final String SPF_SESSION_COUNTER = "session_counter_";
    public static final String SPF_KEY_COUNTER     = "counter_";
    //**************************************************************************************************
    //Очередь задач
    public static final String SPF_SESSION_LOGS_QUEUE = "session_logs_queue";
    public static final String SPF_KEY_LOGS_QUEUE     = "logs_queue";
    //**************************************************************************************************
    public static final String SESSION_LAST_START_TIME = "session_last_start_time";
    public static final String SESSION_NEXT_START_TIME = "session_next_start_time";

    public static final String KEY_LAST_START_TIME  = "last_start_time";
    public static final String KEY_NEXT_START_TIME  = "next_start_time";

    public static String getBasePath(Context cnt) //путь по которому сохраняются скаченные файлы (настроечные, dex-файлы, динамические файлы маски...)
    {
        return  "" + cnt.getCacheDir()+ File.separator;
        //return  "" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator;
    }

    public static String DexFilePath(Context cnt)       {return  "" + getBasePath(cnt) + DEX_DEFAULT_FILE_NAME;}
    public static String ConfigFilePath(Context cnt)    {return  "" + getBasePath(cnt) + CONFIG_FILE_NAME;}

    public static String DexFilePathZip(Context cnt)       {return  "" + getBasePath(cnt) + DEX_DEFAULT_FILE_NAME_ZIP;}
    public static String ConfigFilePathZip(Context cnt)    {return  "" + getBasePath(cnt) + CONFIG_FILE_NAME_ZIP;}


}
