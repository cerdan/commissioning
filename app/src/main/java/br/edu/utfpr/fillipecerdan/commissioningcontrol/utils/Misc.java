package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class Misc {
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

    public static void clearViews(ViewGroup viewGroup){
        int length = viewGroup.getChildCount();
        for (int i = 0; i<length; i++){
            View v = viewGroup.getChildAt(i);
            if(v instanceof EditText){
                ((EditText) v).getText().clear();
            } else if (v instanceof Spinner) {
                ((Spinner) v).setSelection(0);
            } else if (v instanceof RadioGroup) {
                ((RadioGroup) v).clearCheck();
            } else if (v instanceof CheckBox) {
                ((CheckBox) v).setChecked(false);
            }
        }
    }
}
