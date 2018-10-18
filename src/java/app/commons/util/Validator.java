/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package app.commons.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Kai
 */
public class Validator {
    
    protected static Trace t = new Trace(Validator.class);
    
    public Validator(){}
    
    public static boolean isValidId(int id) {
        if(id > 0) return true;
        return false;
    }
    
    public static boolean isNumeric(String s) {
        if(s == null) return false;
        Pattern p = Pattern.compile( "([0-9]*)" );
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
    public static boolean isValidStreet(String name) {
        if(name != null && name.length() > 3) return true;
        return false;
    }
    
    public static boolean isValidZipCode(String s) {
        if(isNumeric(s) && s.length() == 5) return true;
        return false;
    }

    public static boolean isValidCity(String name) {
        if(name != null && name.length() > 2) return true;
        return false;
    }
    /**
     * Is at least 2 characters long.
     */
    public static boolean isValidName(String name) {
        if(name != null && name.length() > 1 && name.length() < 101) return true;
        return false;
    }
    
    public static boolean isValidUserName(String name) {
        if(name != null && name.length() > 5 && name.length() < 31) return true;
        return false;
    }
    
    public static boolean isValidPassword(String name) {
        if(name != null && name.length() > 5 && name.length() < 31) return true;
        return false;
    }
    
    public static boolean isValidGender(String name) {
        if(name != null) {
            if(name.equals("male") || name.equals("female")){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidUserGroup(String name) {
        if(name != null) {
            if(name.equals("STUDENT") || name.equals("ADMIN")){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isValidBirthDay(Calendar bDay) {
        
        if(bDay != null) {
        
            int year = bDay.get(Calendar.YEAR);
            int month = bDay.get(Calendar.MONTH);
            t.out("month = " + month);
            int day = bDay.get(Calendar.DAY_OF_MONTH);
            int daysInMonth = 30;
            int realMonth = month + 1;
            if(realMonth == 1 || realMonth == 3 || realMonth == 5 || realMonth == 7 || realMonth == 8 || 
                    realMonth == 10 || realMonth == 12) {
                daysInMonth = 31;
            }
            Calendar now = MyCalendar.getInstance();
            int nowYear = now.get(Calendar.YEAR);
            t.out("nowYear == " + nowYear);
            if(year > 1909 && year <= nowYear) {
                t.out("year is fine.");
                if (month >= 0 && month < 12) {
                    t.out("month ok.");
                    t.out("day == " + day);
                    t.out("");
                    t.out("daysInMonth == " + daysInMonth);
                    if(day > 0 && day <= daysInMonth) {
                        
                        t.out("palautti truen");
                        return true;
                     
                    }
                }
            }
        }
        MyCalendar.toString("isValid... --> ", bDay);
        t.out("palautti falsen");
        return false;
        
    }
    
    public static boolean isValidHour(int hour) {
        if(hour >= 0 && hour <= 23) return true;
        return false;
    }
    
    public static boolean isValidMinute(int min) {
        if(min >= 0 && min <= 59) return true;
        return false;
    }
    
    public static boolean isValidTimestamp(Timestamp ts) {
        if(ts == null) return false;        
        return true;
    }
    
    



    /**
     * Pretty loose email checking for small applications.
     */
    public static boolean isValidEmail(String email) {
        
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        boolean matchFound = m.matches();
        
        return matchFound;
        
    }
    
    public static boolean isValidPhone(String s) {
        
        t.out("phone == " + s);
        
        if(s == null || s.length() < 6) return false; // make it loose
        /**
         * TODO: make correct regexp
         *
      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
      Matcher m = p.matcher(s);
      boolean matchFound = m.matches();
         */
        return true;
        
    }
    
    public static boolean isEmpty(String s) {
        if(s == null || s.equals("")) return true;
        return false;
    }
    
    
}
