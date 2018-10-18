/*
 * MyCalendar.java
 *
 * Created on 28. tammikuuta 2012, 15:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.commons.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Kaitsu
 */
public abstract class MyCalendar extends Calendar {
    
    //private Calendar calendar = null;

    public static Calendar getInstance() {

        Calendar c = Calendar.getInstance(new Locale("FI", "fi"));
        //MyCalendar mc = (MyCalendar)c;
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(4); // eka koko viikko uutena vuotena ISO-8601 standardin (Eurooppa) mukaan
        
        // System.out.println("c.getTimeZone().toString() == " + c.getTimeZone().toString());
        
        return c;
    }
    
    public static boolean isValidCalendarData(int y, int m, int d, int h, int min) {
        if(y > 1900 && m > -1 && m < 12 && d > 0 && d < 32 && h > -1 && h < 24 && min > -1 && min < 60) return true;
        return false;
    }
    
    public static boolean isValidCalendarData(int h, int min) {
        if(h > -1 && h < 24 && min > -1 && min < 60) return true;
        return false;
    }
    
    /**
     * Set calendars minutes, seconds and milliseconnds to 0, eg. to even hour.
     */
    public static Calendar evenHour(Calendar c) {
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    public static Calendar evenMinute(Calendar c) {
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    public static String getTodayDate() {
        Calendar c = getInstance();
        return Converter.toDateString(c);
    }
    
    public static String getNowTime() {
        Calendar c = getInstance();
        return Converter.toTimeString(c);
    }
    
    public static String getBeginOfDayString(Calendar c) {
        
        Calendar cr = (Calendar)c.clone();
        cr.set(Calendar.HOUR_OF_DAY, 0);
        cr = MyCalendar.evenHour(cr);
        
        return Converter.toMySQLDateTimeString(cr);
    }

    public static String getEndOfDayString(Calendar c) {
        
        Calendar cr = (Calendar)c.clone();
        cr.set(Calendar.HOUR_OF_DAY, 23);
        cr.set(Calendar.MINUTE, 59);
        cr.set(Calendar.SECOND, 59);

        return Converter.toMySQLDateTimeString(cr);
    }

    public static void toString(String s, Calendar c) {
        
        if(c != null) {
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            int h = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);

            System.out.println(s + " " + "CALENDAR: year = " + y + ", month = " + m + ", day = " + d + ", hour = " + h + ", min = " + min);
        }
        else {
            System.out.println("Calendar was NULL!!! From MyCalendar.....");
        }
    }
    
    
    /**
     * //Return eg. 16.09.2013 - 22.08.2013
     * Return eg. 09.05.2016 - 15.05.2016
     */
    public static String getWeekString(Calendar c2) {
        
        String week = "";
        Calendar c = (Calendar)c2.clone();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        if(day.length() == 1) day = "0" + day;
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        if(month.length() == 1) month = "0" + month;
        String year = Integer.toString(c.get(Calendar.YEAR));
        
        week = day + "." + month + "." + year + " - ";
        
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        month = Integer.toString(c.get(Calendar.MONTH) + 1);
        if(day.length() == 1) day = "0" + day;
        if(month.length() == 1) month = "0" + month;
        year = Integer.toString(c.get(Calendar.YEAR));
        
        week = week + " " + day + "." + month + "." + year;
        
        return week;
    }
    
    
    /**
     * ma == 1, ti == 2, etc.
     */
    public static int getWeekDayNumber(Calendar c) {
        
        int n = c.get(Calendar.DAY_OF_WEEK);
        
        if(n == 1) n = 7;
        else n = n - 1;
        
        return n;
        
    }
    
    /**
     * Return eg. "Ma" if shortVersion == true, "Maanantai" else.
     */
    public static String getWeekDay(Calendar c, boolean shortVersion) {
        
        String day = "-";

        if(shortVersion) {
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) day = "Ma";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) day = "Ti";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) day = "Ke";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) day = "To";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) day = "Pe";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) day = "La";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) day = "Su";
        }
        else {
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) day = "Maanantai";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) day = "Tiistai";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) day = "Keskiviikko";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) day = "Torstai";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) day = "Perjantai";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) day = "Lauantai";
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) day = "Sunnuntai";
            
        }
        
        return day;
    }
    
    /**
     * return int minutes between t1 and t2, t2 being after t1.
     */
    public static int getMinutesBetween(Timestamp t1, Timestamp t2) {
        
        long m1 = t1.getTime(); // time in millis after 1970
        long m2 = t2.getTime();
        
        int factor = 1000 * 60;
        
        int minutes = (int)(m2 - m1) / factor;
        
        return minutes;
    }
    
    
}
