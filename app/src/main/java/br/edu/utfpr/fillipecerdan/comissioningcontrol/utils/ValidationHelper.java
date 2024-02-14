package br.edu.utfpr.fillipecerdan.comissioningcontrol.utils;

import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public abstract class ValidationHelper{
    public static boolean isValid(TextView item, String invalidMsg) {
        if (item == null || item.getText().toString().trim().length() == 0) {
            if(invalidMsg.length()>0)
                Toast.makeText(item.getContext(), invalidMsg, Toast.LENGTH_SHORT).show();
            item.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValid(TextView item) {
        return isValid(item, "");
    }

    public static boolean isValid(RadioGroup item, String invalidMsg) {
        if (item == null || item.findViewById(item.getCheckedRadioButtonId()) == null) {
            if(invalidMsg.length()>0)
                Toast.makeText(item.getContext(), invalidMsg, Toast.LENGTH_SHORT).show();
            item.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValid(RadioGroup item) {
        return isValid(item, "");
    }

    public static boolean isValid(CheckBox item, String invalidMsg) {
        if (item == null) {
            if(invalidMsg.length()>0)
                Toast.makeText(item.getContext(), invalidMsg, Toast.LENGTH_SHORT).show();
            item.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValid(CheckBox item) {
        return isValid(item, "");
    }
}
