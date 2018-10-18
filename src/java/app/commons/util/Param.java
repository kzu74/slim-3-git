/*
 * Param.java
 *
 * Created on 14. elokuuta 2011, 18:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package app.commons.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Kaitsu
 */
public class Param {

    static Trace t = new Trace(Param.class);

    public static Integer getInteger(HttpServletRequest req,
            String reqParameter) {

        Integer n = new Integer(-1);
        try {
            Object obj = req.getParameter(reqParameter);
            if(obj == null) return n;
            String s = (String)obj;
            n = Integer.parseInt(s);
        }
        catch(Exception e) {
            t.out("Exception heitettiin...");
            n = -1;
            //e.printStackTrace();
        }
        return n;

    }

    public static Integer getInteger(HttpServletRequest req,
            String reqParameter, int defaultValue) {

        Integer n = new Integer(-1);
        try {
            String s = req.getParameter(reqParameter);
            if(s == null) return defaultValue;
            n = Integer.parseInt(s);
        }
        catch(Exception e) {
            e.printStackTrace();
            n = new Integer(defaultValue); 
        }
        return n;

    }

    public static Long getLong(HttpServletRequest req,
            String reqParameter, Long defaultValue) {

        Long n = new Long(-1);
        try {
            String s = req.getParameter(reqParameter);
            if(s == null) return defaultValue;
            n = Long.parseLong(s);
        }
        catch(Exception e) {
            e.printStackTrace();
            n = new Long(defaultValue); 
        }
        return n;

    }
    
    
    public static BigDecimal getBigDecimal(HttpServletRequest req,
            String reqParameter) {


        BigDecimal bd = null;
        try {
            Object obj = req.getParameter(reqParameter);
            if(obj == null) return null;
            String s = (String)obj;
            s = s.replace(',', '.');
            bd = new BigDecimal(s);
        }
        catch(Exception e) {
            t.out("Exception heitettiin...");
            bd = null;
            //e.printStackTrace();
        }
        return bd;

    }

    public static double getDouble(HttpServletRequest req,
            String reqParameter, double defaultValue) {

        double d = 0.0;
        try {
            String s = req.getParameter(reqParameter);

            if(s == null) return 0.0;
            
            s = s.replace(',', '.');
            
            d = Double.parseDouble(s);
        }
        catch(Exception e) {
            t.out("Exception heitettiin...");
            d = 0.0;
            e.printStackTrace();
        }
        return d;

    }
    public static float getFloat(HttpServletRequest req,
            String reqParameter, float defaultValue) {

        float d = 0.0f;
        try {
            String s = req.getParameter(reqParameter);

            if(s == null) return 0.0f;
            
            s = s.replace(',', '.');
            
            d = Float.parseFloat(s);
            
            t.out("parse float req == " + reqParameter + ", str = " + s + " ja parsed == " + d);
        }
        catch(Exception e) {
            t.out("Exception heitettiin...");
            d = 0.0f;
            e.printStackTrace();
        }
        return d;

    }

    /**
     *
     * @param req
     * @param reqParamName
     * @param defaultValue == default value when reqParameter is not found.
     * @return the value of the reqParamName
     */
    public static int getInt(HttpServletRequest req,
            String reqParamName, int defaultValue) {
            int n = defaultValue;
        try {
            String obj = req.getParameter(reqParamName);
            if(obj == null) return defaultValue;
            String s = (String)obj;
            n = Integer.parseInt(s);
        }
        catch(Exception e) {
            t.out("Exception heitettiin...");
            //e.printStackTrace();
        }
        return n;

    }

    public static String getString(HttpServletRequest req, String reqParamName) {

        String s = req.getParameter(reqParamName);
        if(s == null) return null;
        s.trim();
        return s;

    }

    public static String getString(HttpServletRequest req, String reqParamName, String defaultValue) {

        String s = req.getParameter(reqParamName);
        if(s == null) return defaultValue;
        s.trim();
        return s;

    }

    /**
     * For html checkboxes.
     */
    public static boolean getBoolean(HttpServletRequest req, String reqParamName) {

        String s = req.getParameter(reqParamName);
        if(s != null) return true; // checked c

        return false;
    }
    
    
    public static String getStringAttribute(HttpServletRequest req,
            String reqAttrName) {

        String s = null;
        Object obj = req.getAttribute(reqAttrName);
        if(obj == null) return null;
        s = (String)obj;
        return s;

    }

    public static String getStringAttribute(HttpServletRequest req,
            String reqAttrName, String defaultValue) {

        String s = null;
        Object obj = req.getAttribute(reqAttrName);
        if(obj == null) return defaultValue;
        s = (String)obj;
        return s;

    }

    public static Integer getIntegerAttribute(HttpServletRequest req,
            String reqAttrName) {

        Integer s = new Integer(-1);
        Object obj = req.getAttribute(reqAttrName);
        if(obj == null) return s;
        s = (Integer)obj;
        return s;

    }

    public static Integer getIntegerAttribute(HttpServletRequest req,
            String reqAttrName, Integer defaultValue) {

        Integer s = new Integer(-1);
        Object obj = req.getAttribute(reqAttrName);
        if(obj == null) return defaultValue;
        s = (Integer)obj;
        return s;

    }
    
    /**
     * Receives "year", "month" and "day" http params and returns user picked 
     * CalendarDate (java.util.Calendar), otherwise Calendar presenting
     * current date. Month is received between 0 - 11.
     *
     * TODO: validate!
     */
    public static Calendar getCalendarDate(HttpServletRequest req) {
        
        Calendar c = MyCalendar.getInstance();
        
        int year = getInt(req, "year", c.get(Calendar.YEAR));
        int month = getInt(req, "month", c.get(Calendar.MONTH));
        int day = getInt(req, "day", c.get(Calendar.DAY_OF_MONTH));
        
        if(!MyCalendar.isValidCalendarData(year, month, day, 0, 0)) return null;
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        
        return c;
    }

    /**
     * Receives "year", "month" and "day" http params and returns user picked 
     * CalendarDate (java.util.Calendar), otherwise Calendar presenting
     * current date. Month is received between 1-12 and then -1 = month in calendar.
     *
     * TODO: validate!
     */
    public static Calendar getCalendarDateMonth12(HttpServletRequest req) {
        
        Calendar c = MyCalendar.getInstance();
        
        int year = getInt(req, "year", c.get(Calendar.YEAR));
        int month = getInt(req, "month", c.get(Calendar.MONTH));
        month--;
        int day = getInt(req, "day", c.get(Calendar.DAY_OF_MONTH));
        
        if(!MyCalendar.isValidCalendarData(year, month, day, 0, 0)) {
            System.out.println("Toteutui !MyCalendar.isValidCalendarData(), day == " + day + ", month == " + month + ", year == " + year);
            return null;
        }
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        
        return c;
    }
    
    /**
     * Receives "year", "month", "day", "hour", "minute" http params and returns 
     * a Calendar instance if data is valid, and null if data is not valid.
     */
    public static Calendar getCalendarDateTime(HttpServletRequest req) {
        
        Calendar c = MyCalendar.getInstance();
        
        int year = getInt(req, "year", -1);
        int month = getInt(req, "month", -1);
        int day = getInt(req, "day", -1);
        int hour = getInt(req, "hour", -1);
        int minute = getInt(req, "minute", -1);
                
        if(!MyCalendar.isValidCalendarData(year, month, day, hour, minute)) return null;
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }

    public static Calendar getCalendarStartDateTime(HttpServletRequest req) {
        
        Calendar c = MyCalendar.getInstance();
        
        int year = getInt(req, "syear", -1);
        int month = getInt(req, "smonth", -1);
        int day = getInt(req, "sday", -1);
        int hour = getInt(req, "shour", -1);
        int minute = getInt(req, "sminute", -1);
                
        if(!MyCalendar.isValidCalendarData(year, month, day, hour, minute)) return null;
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }

    public static Calendar getCalendarEndDateTime(HttpServletRequest req) {
        
        Calendar c = MyCalendar.getInstance();
        
        int year = getInt(req, "eyear", -1);
        int month = getInt(req, "emonth", -1);
        int day = getInt(req, "eday", -1);
        int hour = getInt(req, "ehour", -1);
        int minute = getInt(req, "eminute", -1);
                
        if(!MyCalendar.isValidCalendarData(year, month, day, hour, minute)) return null;
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    /**
     * Parses req parameters date=24.12.2012 and start=09:15 and end=10:15 and returns Calendar
     * object.
     */
    public static Calendar getCalendarSameDate(HttpServletRequest req, boolean startTime) {
        
        String date = getString(req, "date", null);
        String time = null;
        if(startTime) {
            time = getString(req, "start", null);
        }
        else {
            time = getString(req, "end", null);
        }
/*        System.out.println("date == " + date);
        System.out.println("time == " + time);
        
        System.out.println("1...............................");
 */
        if(date == null || time == null) return null;
                
        String[] dmy = date.split("\\.");
/*        
        
        System.out.println("2...............................");
        System.out.println("dmy == " + dmy);
        System.out.println("dmy len == " + dmy.length);
 */
        if(dmy == null || dmy.length != 3) return null;
        
        System.out.println("3...............................");
        if(dmy[0].length() != 2 || dmy[1].length() != 2 || dmy[2].length() != 4) return null;
        
        String[] hm = time.split(":");

        System.out.println("4...............................");
        
        if(hm == null || hm.length != 2) return null;
        //System.out.println("5...............................");
        
        if(hm[0].length() != 2 || hm[1].length() != 2) return null;
        
        if(dmy[0].substring(0,1).equals("0")) { // eg. 01.12.2012
            dmy[0] = dmy[0].substring(1,2);
            if(dmy[0].equals("0")) return null;
        }
        //System.out.println("6...............................");
        
        if(dmy[1].substring(0,1).equals("0")) { // eg. 24.01.2012
            dmy[1] = dmy[1].substring(1,2);
            if(dmy[1].equals("0")) return null;
        }
        //System.out.println("7...............................");
        
        if(hm[0].substring(0,1).equals("0")) { // eg. 09:15
            hm[0] = hm[0].substring(1,2);
            if(hm[0].equals("0")) return null;
        }
        //System.out.println("8...............................");

        if(hm[1].substring(0,1).equals("0")) { // eg. 16:00
            hm[1] = hm[1].substring(1,2);
        }
                
        //System.out.println("9...............................");
        int day = Converter.toInt(dmy[0], -1);
        int month = Converter.toInt(dmy[1], -1);
        int year = Converter.toInt(dmy[2], -1);
        
        int hour = Converter.toInt(hm[0], -1);
        int min = Converter.toInt(hm[1], -1);
        
        month--; // for calendar (0 - 11)
/*        
        System.out.println("day == " + day);
        System.out.println("month == " + month);
        System.out.println("year == " + year);
        System.out.println("hour == " + hour);
        System.out.println("min == " + min);
  */      
        // TODO: is legal hour!!!
    //    System.out.println("10...............................");
//        if(!TimesConfig.isLegalMinute(min)) return null;
        
       // System.out.println("11...............................");
        if(!MyCalendar.isValidCalendarData(year, month, day, hour, min)) return null;
        //System.out.println("12................................");
        Calendar c = MyCalendar.getInstance();
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    /**
     * Parses req parameters date=24.12.2012 and returns Calendar object.
     */
    public static Calendar getCalendarDateDotSeparated(HttpServletRequest req, String paramName) {
        
        String date = getString(req, paramName, null);

        //System.out.println("1......................");
        if(date == null) return null;
        
        //System.out.println("2......................");
                
        String[] dmy = date.split("\\.");

        if(dmy == null || dmy.length != 3) return null;
        
        //System.out.println("3......................");

        if(dmy[0].length() != 2 || dmy[1].length() != 2 || dmy[2].length() != 4) return null;
        
        //System.out.println("4......................");

        if(dmy[0].substring(0,1).equals("0")) { // eg. 01.12.2012
            dmy[0] = dmy[0].substring(1,2);
            if(dmy[0].equals("0")) return null;
        }
        //System.out.println("5......................");
        
        if(dmy[1].substring(0,1).equals("0")) { // eg. 24.01.2012
            dmy[1] = dmy[1].substring(1,2);
            if(dmy[1].equals("0")) return null;
        }
        //System.out.println("6......................");
        
        int day = Converter.toInt(dmy[0], -1);
        int month = Converter.toInt(dmy[1], -1);
        int year = Converter.toInt(dmy[2], -1);
        
        month--;
        
        //System.out.println("Param day == " + day + " month == " + month + " year == " + year);
        
        if(!MyCalendar.isValidCalendarData(year, month, day, 1, 1)) return null;

        //System.out.println("7......................");
        Calendar c = MyCalendar.getInstance();
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
    }
    
    
    /**
     * Receive eg. 18.03.2013 as dateParamName and timeParamName 09:30
     * 
     * Return java.sql.Timestamp or null if formatting of dateParamName or timeParamName is wrong.
     */
    public static Timestamp getTimestamp(HttpServletRequest req, String dateParamName, String timeParamName) {
        
        Timestamp ts = null;
        
        Calendar c = getCalendarDateDotSeparated(req, dateParamName);
        
        if(c == null) return null;
        
        String time = req.getParameter(timeParamName);
        
        if(time == null) return null;
        
        String[] hourMin = time.split(":");
        
        if(hourMin.length == 2) {

            String hour = hourMin[0];
            String min = hourMin[1];

            if(hour.startsWith("0")) {
                hour = hour.substring(1);
            }

            int h = Integer.parseInt(hour);
            
            if(min.startsWith("0")) {
                min = min.substring(1);
            }
            int m = Integer.parseInt(min);
            
            if(Validator.isValidHour(h) && Validator.isValidMinute(m)) {
                c.set(Calendar.HOUR_OF_DAY, h);
                c.set(Calendar.MINUTE, m);
                ts = Converter.toTimestamp(c);
            }
        }
        
        return ts;
        
    }
        
}
