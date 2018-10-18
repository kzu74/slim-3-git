/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.commons.util;

import java.math.BigDecimal;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kai
 */
public class Converter {

    static Trace t = new Trace(Converter.class);
    
    public static String exceptionToStackTraceString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static int toInt(String s, int def) {
        int n = def;
        try {
            n = Integer.parseInt(s);
        }
        catch(Exception e) {}
        
        return n;
    }

    public static Integer toInteger(String s, Integer def) {
        Integer n = def;
        try {
            n = Integer.parseInt(s);
        }
        catch(Exception e) {}

        return n;
    }

    public static List<Integer> toIntegerList(String[] s) {
        
        List<Integer> list = null;
        try {
            if(s != null && s.length > 0) {
                list = new ArrayList<Integer>();
                for(int i=0; i < s.length; i++) {
                    int n = Integer.parseInt(s[i]);
                    list.add(n);
                }
            }
        }
        catch(Exception e) {}

        return list;
    }

    public static List<Long> toLongList(String[] s) {
        
        List<Long> list = null;
        try {
            if(s != null && s.length > 0) {
                list = new ArrayList<Long>();
                for(int i=0; i < s.length; i++) {
                    long n = Long.parseLong(s[i]);
                    list.add(n);
                }
            }
        }
        catch(Exception e) {}

        return list;
    }
    
    public static BigDecimal toBigDecimal(String s, BigDecimal def) {


        BigDecimal bd = null;
        try {
            if(s == null) return def;
            s = s.replace(',', '.');
            bd = new BigDecimal(s);
        }
        catch(Exception e) {
            t.out("Exception thrown in toBigDecimal(), s == " + s);
            bd = def;
        }
        return bd;

    }
    
    public static String bigDecimalToString(BigDecimal bd) {

        if(bd == null) return "";
        String bdStr = bd.toString();
        bdStr = bdStr.replace('.', ',');
        return bdStr;

    }

    public static int bigDecimalToInt(BigDecimal bd) {

        if(bd == null) return 0;
        int bdInt = bd.intValue();

        return bdInt;

    }
    
    /**
     * Takes a float, eg. 16.0485222 and returns "16,05", if decimals == 2.
     */
    public static String roundedValue(float n, int decimals) {
        BigDecimal roundBD = new BigDecimal(n).setScale(decimals ,BigDecimal.ROUND_HALF_UP);
        return bigDecimalToString(roundBD);
    }
    
    /** 
     * Takes a float, eg. 16.0485222 and returns "16,05", if decimals == 2.
     * 
     * TODO: it returns int so what doing with decimals?!?
     */
    public static int roundedValueInt(float n, int decimals) {
        BigDecimal roundBD = new BigDecimal(n).setScale(decimals ,BigDecimal.ROUND_HALF_UP);
        return bigDecimalToInt(roundBD);
    }
    
    public static int roundUp(float n) {
        BigDecimal roundBD = new BigDecimal(n).setScale(0, BigDecimal.ROUND_HALF_UP);
        return bigDecimalToInt(roundBD);
    }
    
    
    
    /**
     * Returns dd.MM.yyyy string, eg. 24.01.2012
     */
    public static String toDateString(Calendar c) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(c.getTime());
    }

    /**
     * Returns dd.MM.yyyy klo HH:mm string, eg. '24.01.2012'
     */
    public static String toDateString(Timestamp c) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        t.out("SDFFFFFFFFFFFFFFFFFFFF == " + sdf);
        if(sdf != null) {
            return sdf.format(c.getTime());
        }
        return null;
    }
    
    /**
     * Returns dd.MM.yyyy klo HH:mm string, eg. '24.01.2012 klo 18:05'
     */
    public static String toDateTimeString(Timestamp c) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(c.getTime());
        sdf.applyPattern("HH:mm");
        String time = sdf.format(c.getTime());
        String res = date + " klo " + time;
        return res;
    }
    
    /**
     * Returns dd.MM.yyyy string, eg. <b>Ke</b> 24.01.2012
     */
    public static String toDateStringPrefix(Calendar c) {

        SimpleDateFormat sdf = new SimpleDateFormat("E dd.MM.yyyy");
        String s = sdf.format(c.getTime());
        String first = s.substring(0,1);
        String upper = first.toUpperCase();
        s = s.replaceFirst(first, upper);

        return s;
        
    }
    /**
     * Returns dd.MM.yyyy string, eg. <b>Ke</b> 24.01.2012
     */
    public static String toDateStringPrefix(Timestamp c) {

        SimpleDateFormat sdf = new SimpleDateFormat("E dd.MM.yyyy");
        String s = sdf.format(c.getTime());
        String first = s.substring(0,1);
        String upper = first.toUpperCase();
        s = s.replaceFirst(first, upper);

        return s;
        
    }

    /**
     * Returns HH:mm string, eg. 09:30
     */
    public static String toTimeString(Calendar c) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");        
        return sdf.format(c.getTime());
    }
    
    /**
     * Returns HH:mm string, eg. 09:30
     */
    public static String toTimeString(Timestamp ts) {

        Calendar c = toCalendar(ts);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");        
        return sdf.format(c.getTime());
    }
    
    
    public static String toMySQLDateTimeString(Calendar c) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c.getTime());
    }

    public static String toMySQLDateString(Calendar c) {
        if(c == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }
    /**
     * Takes calendar instance holding date info, eg. 29.01.2012 and String time
     * eg. 09:30 and combines them to MySQL ready Datetime string.
     */
    public static String toMySQLDateTimeString(Calendar date, String time) {

        String[] hourMin = time.split(":");
        if(hourMin[0].startsWith("0")) {
            hourMin[0] = hourMin[0].substring(1); // take off the first zero
        }
        int hour = toInt(hourMin[0], 23);
        int min = toInt(hourMin[1], 23);
        date.set(Calendar.HOUR_OF_DAY, hour);
        date.set(Calendar.MINUTE, min);
        date.set(Calendar.SECOND, 0);
        
        return toMySQLDateTimeString(date);
    }

    /**
     * Takes calendar instance holding date info, eg. 29.01.2012 and String time
     * eg. 09:30 and combines them and returns new MyCalendar instance. MyCalendar,
     * because all Calendar instances are originated from MyCalendar.getInstance().
     */
    public static Calendar toCalendarDateTime(Calendar date, String time) {

        Calendar c = (Calendar)date.clone();
        String[] hourMin = time.split(":");
        if(hourMin[0].startsWith("0")) {
            hourMin[0] = hourMin[0].substring(1); // take off the first zero
        }
        
        int hour = toInt(hourMin[0], -1);
        int min = toInt(hourMin[1], -1);
        
        if(!MyCalendar.isValidCalendarData(hour, min)) return null;
        
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        
        return c;
    }
    
    
    /**
     * Takes mysqlDate, eg 24-02-2011 and time, eg. 09:30 and returns "24-02-2011 09:30:00". 
     */
    public static String toMySQLDateTimeString(String mysqlDate, String time) {
    
        return mysqlDate + " " + time + ":00";
    }
    
    public static String toMySQLDateTimeString(Date c) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c);
    }

    public static String toMySQLDateTimeString(Timestamp c) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(c);
    }

    
    /**
     * to java java.sql.Timestamp
     */
    public static Timestamp toTimestamp(Calendar c) {
        java.sql.Timestamp t = new java.sql.Timestamp(c.getTimeInMillis());
        return t;
    }

    /**
     * To java.sql.Date
     */
    public static java.sql.Date toDate(Calendar c) {
        java.sql.Date t = new java.sql.Date(c.getTimeInMillis());
        return t;
    }
    
    
    /**
     * Takes eg 19.09.2012 and return corresponding MyCalendar object.
     */
    public static Calendar toCalendar(String dotDate) {
        
        Calendar c = MyCalendar.getInstance();
        String[] dmy = dotDate.split("\\.");
        c.set(Calendar.DAY_OF_MONTH, new Integer(dmy[0]));
        c.set(Calendar.MONTH, new Integer(dmy[1]) - 1);
        c.set(Calendar.YEAR, new Integer(dmy[2]));
        
        return c;
    }
    
    public static Calendar toCalendar(Timestamp t) {
        Calendar c = MyCalendar.getInstance();
        c.setTime(t);
        return c;
    }

    public static Calendar toCalendar(java.sql.Date date) {
        Calendar c = MyCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        MyCalendar.evenHour(c);
        c.setTime(date);
        return c;
    }
    
    
    
    /**
     * Replace "ääkköset" to Browser independent
     */
    public static String toHTMLText(String s) {

        s = s.replaceAll("ä", "&auml;");
        s = s.replaceAll("ö", "&ouml;");
        s = s.replaceAll("Ä", "&Auml;");
        s = s.replaceAll("Ö", "&Ouml;");

        return s;
    }
    

}
