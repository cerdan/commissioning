package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static final String KEY_EQUIPMENT = "equipment";
    public static final String KEY_RENAME= "lastName";
    public static final int NOT_FOUND = -1;
    public static final String PREFERENCES = "br.edu.utfpr.fillipecerdan.commissioningcontrol.PREFERENCES";
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