package com.demo.excel.demoexcel.Utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeDate {

    private static final String PATTERN = "dd/MM/yyyy HH:mm:ss";

    public static String timeDate(){
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        String date = simpleDateFormat.format(new Date());
        return date;
    }




}
