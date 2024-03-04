package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import br.edu.utfpr.fillipecerdan.commissioningcontrol.R;

public abstract class Misc {
    public static final String TAG_FOR_LOG = "Fillipe";

    public static final Date parseDate(String date, SimpleDateFormat formatter) {
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Date parseDate(String date) {
        return parseDate(date, new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH));

    }

    public static final void log(Object msg){
        if (msg == null) msg = "Object is Null";
        Log.w(TAG_FOR_LOG, msg.toString());
    }

    public static final void clearViews(ViewGroup viewGroup){
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

    public static final void displayWarning(Context context, int msgId){
        AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(context);

        alertBuilder.setTitle(R.string.lblStringWarningTitle);
        alertBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertBuilder.setMessage(msgId);

        alertBuilder.setNeutralButton(android.R.string.ok,
                    (dialog, which) -> {}
                );

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static final void confirmAction(Context context, String msg, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder alertBuilder = new  AlertDialog.Builder(context);

        alertBuilder.setTitle(R.string.lblStringConfirmationTitle);
        alertBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        alertBuilder.setMessage(msg);

        alertBuilder.setPositiveButton(R.string.yes, onClickListener);
        alertBuilder.setNegativeButton(R.string.no, onClickListener);

        AlertDialog alert = alertBuilder.create();
        alert.show();

    }

}
