package br.edu.utfpr.fillipecerdan.comissioningcontrol.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Misc {
    public static final String KEY_EQUIPMENT = "equipment";
    public static final String TAG_FOR_LOG = "Fillipe";

    public static Date parseDate(String date, SimpleDateFormat formatter) {
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static Date parseDate(String date) {
        return parseDate(date, new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH));

    }

    public static void log(Object msg){
        Log.w(TAG_FOR_LOG, msg.toString());
    }
}
