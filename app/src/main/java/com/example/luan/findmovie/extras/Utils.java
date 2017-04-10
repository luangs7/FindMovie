package com.example.luan.findmovie.extras;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by luan on 28/07/16.
 */
public class Utils {


    public static String FormatDate(String stringData){

        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date data = formato.parse(stringData);
            formato.applyPattern("dd/MM/yyyy");
            return  formato.format(data);
        }catch (Exception e){
            return stringData;
        }

    }

    public static String formatCalendar(Calendar calendar){
//        calendar = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String formatted = format1.format(calendar.getTime());
        return formatted;

    }



}
