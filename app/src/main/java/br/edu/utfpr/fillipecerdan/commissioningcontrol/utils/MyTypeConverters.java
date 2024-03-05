package br.edu.utfpr.fillipecerdan.commissioningcontrol.utils;

import androidx.room.TypeConverter;

import java.util.Date;

import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentStatus;
import br.edu.utfpr.fillipecerdan.commissioningcontrol.model.EquipmentType;

public class MyTypeConverters {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return (dateLong == null) ? null : new Date(dateLong);
    }

    @TypeConverter
    public static long fromDate(Date date){
        return (date == null) ? null : date.getTime();
    }

    @TypeConverter
    public static EquipmentType toEquipmentType(int eqpTypeInt){
        return EquipmentType.values()[eqpTypeInt];
    }

    @TypeConverter
    public static int fromEquipmentType(EquipmentType eqpType){
        return eqpType.ordinal();
    }
    @TypeConverter
    public static EquipmentStatus toEquipmentStatus(int eqpStatusInt){
        return EquipmentStatus.values()[eqpStatusInt];
    }

    @TypeConverter
    public static int fromEquipmentStatus(EquipmentStatus eqpStatus){
        return eqpStatus.ordinal();
    }
}
