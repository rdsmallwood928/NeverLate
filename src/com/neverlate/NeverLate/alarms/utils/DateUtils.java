package com.neverlate.NeverLate.alarms.utils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 1/20/14
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {


    //I suppose joda-time would be better to use instead of this class, but sometimes when learning its better to re-invent the wheel

    public static long MILLISECOND = 1;
    public static long SECOND = 1000 * MILLISECOND;
    public static long MINUTE = 60 * SECOND;
    public static long HOUR = 60 * MINUTE;
    public static long DAY = 24 * HOUR;
    public static long WEEK = 7 * DAY;


    public static Date getStartOfDate(Date date) {
        long numberOfDays = (long)Math.floor(date.getTime()/DAY);
        return new Date((numberOfDays * DAY) + (date.getTimezoneOffset()*MINUTE));
    }
}
