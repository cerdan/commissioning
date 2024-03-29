package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.app.Application;
import android.content.Context;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentType;

public class App extends Application {

    public static final String KEY_EQUIPMENT = "EQUIPMENT";
    public static final String KEY_PROJECT = "PROJECT";
    public static final String KEY_CURRENT_PROJECT_ID = "CURRENT_PROJECT_ID";
    public static final String PREFERENCES = "br.edu.utfpr.fillipecerdan.commissioningcontrol.PREFERENCES";
    public static final String KEY_PREF_ORDER_EQUIPMENT = "ORDER_EQUIPMENT";
    public static final String KEY_PREF_ORDER_PROJECT = "ORDER_PROJECT";
    public static final int PREF_ORDER_DEFAULT= 0;
    public static final int PREF_ORDER_TAG_ONLY = 1;
    public static final int PREF_ORDER_NOK_FIRST= 2;
    public static final int PREF_ORDER_OK_FIRST= 3;
    public static final int PREF_ORDER_LAST_CHANGE = 4;
    public static final int PREF_ORDER_PRJ_CUSTOMER = 1;
    public static final int PREF_ORDER_PRJ_LOCATION = 2;
    public static final int PREF_ORDER_PRJ_YEAR = 3;
    public static final String KEY_PREF_SUGGEST_TYPE = "SUGGEST_TYPE";
    public static final String KEY_PREF_LAST_TYPE = "LAST_TYPE";
    public static final boolean PREF_SUGGEST_TYPE_DEFAULT = true;
    public static final int PREF_LAST_TYPE_DEFAULT = EquipmentType.DSV.ordinal();
    public static final long NOT_FOUND = -1;
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