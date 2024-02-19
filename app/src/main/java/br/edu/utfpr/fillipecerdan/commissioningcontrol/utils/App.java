package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static final String KEY_EQUIPMENT = "EQUIPMENT";
    public static final String KEY_RENAME= "LAST_NAME";
    public static final String PREFERENCES = "br.edu.utfpr.fillipecerdan.commissioningcontrol.PREFERENCES";
    public static final String KEY_PREF_ORDER= "ORDER";
    public static final int PREF_ORDER_DEFAULT= 0;
    public static final int PREF_ORDER_ALPHABETICAL= 1;
    public static final int PREF_ORDER_NOK_FIRST= 2;
    public static final int PREF_ORDER_OK_FIRST= 3;
    public static final int PREF_ORDER_LAST_CHANGE = 4;
    public static final String KEY_PREF_SUGGEST_TYPE = "SUGGEST_TYPE";
    public static final String KEY_PREF_LAST_TYPE = "LAST_TYPE";
    public static final int NOT_FOUND = -1;
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}