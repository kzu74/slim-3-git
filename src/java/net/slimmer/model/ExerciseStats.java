/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

import app.commons.util.Converter;
import app.commons.util.MyCalendar;
import java.util.Calendar;

/**
 *
 * @author Kaitsu
 */
public class ExerciseStats {
    
    private int calories = 0; // daily calories
    private int exerciseCount = 0; // how many exercises per day
    private int minutes = 0; // total minutes per day
    private String date = ""; // the day in string
    private Calendar calendarDate = MyCalendar.getInstance();
    
    public String getDatePrefix() {
        return Converter.toDateStringPrefix(calendarDate);
    }
    

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(int exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public Calendar getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Calendar calendarDate) {
        this.calendarDate = calendarDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    
    
}
