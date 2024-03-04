package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class MyTypeConverters {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return (dateLong == null) ? null : new Date(dateLong);
    }

    @TypeConverter
    public static long fromDate(Date date){
        return (date == null) ? null : date.getTime();
    }
}
