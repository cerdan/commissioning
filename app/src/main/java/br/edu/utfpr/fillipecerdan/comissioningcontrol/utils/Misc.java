package br.edu.utfpr.fillipecerdan.comissioningcontrol.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Misc {
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
}
