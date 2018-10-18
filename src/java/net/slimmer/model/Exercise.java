/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

import app.commons.util.Converter;
import app.commons.util.MyCalendar;
import app.commons.util.Trace;
import app.commons.util.Validator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kaitsu
 */
public class Exercise extends Model {
    
    private String name = "";
    private String comments = "";
    
    private Timestamp startTime = null;
    private Timestamp endTime = null;
    
    private int power = 0;
    private int userWeight = 0;
    private int calories = 0;
    
    private int userId = 0;
    
    public static final int MIN_FACTOR = 1;
    public static final int MAX_FACTOR = 10;
    
    private Sport sport = null;
    private int sportId = 0;
    
    Trace t = new Trace();
    
    public Exercise() {}
    
    public Exercise(int userWeight) {
        this.userWeight = userWeight;
    }
    
    public Exercise(int id, String name, String comments, Timestamp startTime, Timestamp endTime, int power, int userWeight, int sportId) {
    
        this.id = id;
        this.name = name;
        this.comments = comments;
        this.startTime = startTime;
        this.endTime = endTime;
        this.power = power;
        this.userWeight = userWeight;
        this.sportId = sportId;
    }
    
    /**
     * eg. 24.01.2013
     */
    public String getDate() {
        if(startTime != null) {
            return Converter.toDateString(startTime);
        }
        else {
            return Converter.toDateString(MyCalendar.getInstance());
        }
    }
    
    
    /**
     * eg. La 28.12.2013
     */
    public String getDateWeekdayPrefix() {
        if(startTime != null) {
            return Converter.toDateStringPrefix(startTime);
        }
        else {
            return Converter.toDateStringPrefix(MyCalendar.getInstance());
        }
    }
    
    /**
     * eg. "09:35"
     */
    public String getStartTimeString() {
        if(startTime != null) {
            return Converter.toTimeString(startTime);
        }
        else {
            return Converter.toTimeString(MyCalendar.getInstance());
        }
    }

    /**
     * eg. "09:35"
     */
    public String getEndTimeString() {
        if(endTime != null) {
            return Converter.toTimeString(endTime);
        }
        else {
            return Converter.toTimeString(MyCalendar.getInstance());
        }
    }
    
    public int getCalories() {
        
        if(calories == 0) {
        
            int cals = 0;

            int minutes = getMinutes();
            float minutesFactor = (float) minutes / 60;
            int minCal = sport.getMinCalories();
            int maxCal = sport.getMaxCalories();

            float xFactor = (float)(maxCal - minCal) / (MAX_FACTOR - MIN_FACTOR); // x-factor... :-)

            float zeroPoint = MIN_FACTOR * xFactor; // in xy-axis where x = 0

            float weightFactor = (float)userWeight / Sport.PERSON_WEIGHT; // sport.getMin/MaxCalories are based on 70kg person's weight

            float fcals = (minutesFactor * weightFactor * power * xFactor) + zeroPoint; // calories

            cals = Converter.roundUp(fcals); // calories as integer
            
            t.out("----> minFactor == " + minutesFactor + " weightFactor == " + weightFactor); 

            return cals;
        
        }
        else {
            return calories;
        }
    }
    
    public void setCalories(int c) {
        calories = c;
    }

    /**
     * Return minutes between startTime and endTime.
     */
    public int getMinutes() {
        
        if(startTime != null && endTime != null) {
            return MyCalendar.getMinutesBetween(startTime, endTime);
        }
        
        return 0;
    }

    /**
     * TODO: calc minutes
     */
    public void setMinutes(int minutes) {
        //this.minutes = minutes;
    }
    
    public List<String> getInvalidFieldMessages() {
                
        List<String> fields = new ArrayList<String>();
        
        if(!Validator.isValidName(name)) fields.add("- Nimen tulee olla vähintään 2 merkkiä pitkä.");
        
        return fields;
        
    }
    
    /** basic getters and setters **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    
    
    
    
    
    
    
}
