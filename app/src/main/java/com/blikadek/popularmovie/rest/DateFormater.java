package com.blikadek.popularmovie.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by M13x5aY on 25/08/2017.
 */

public class DateFormater {

    public static String getDate(String date){
        String result="";
        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
            result = newFormat.format(oldDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
