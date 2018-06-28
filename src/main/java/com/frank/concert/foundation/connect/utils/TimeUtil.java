package com.frank.concert.foundation.connect.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil
{

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String getCurDate()
    {
        String dateString = df.format(new Date());
        return dateString;
    }

    public static Date getDateByString(String dateString) throws ParseException
    {
        Date date = df.parse(dateString);
        return date;
    }

}
