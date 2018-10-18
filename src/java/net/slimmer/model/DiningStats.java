/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

import app.commons.util.Converter;
import app.commons.util.MyCalendar;
import app.commons.util.Trace;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Kaitsu
 */
public class DiningStats {
    
    private int energy = 0; // eg. energy eaten per day
    private String date = ""; // the day
    private int diningsCount = 0; // how many dining per eg. day
    private int vegetableGrams = 0; // daily vegetables
    private float price = 0.0f;
    private Calendar calendarDate = MyCalendar.getInstance(); // date in calendar format
    
    static Trace t = new Trace(DiningStats.class);
    //private int weeksAverageEnergy = 0;
    
    public static List<Integer> getWeeklyAverageEnergies(List<DiningStats> dsList) { // jäin tähän: viikottaiset energiakeskarvot! :-)
        
        List<Integer> list = new ArrayList<Integer>();
        
        Calendar cFirst = dsList.get(0).getCalendarDate();
        
        int weekDayNumber = 0; // ma = 1, ti = 2 etc...
        
        weekDayNumber = MyCalendar.getWeekDayNumber(cFirst);
        
        t.out("weekDayNumber == " + weekDayNumber);
        
        int firstLoop = 7 - weekDayNumber + 1;
        
        t.out("firstLoop == " + firstLoop);
        
        if(dsList.size() < firstLoop) firstLoop = dsList.size(); //esim. vain keskiviikkona ruokailtu
        
        int energySum = 0;
        
        int index = 0; // jäin tähän 24.09.2013
        
        for(int i = 0; i < firstLoop; i++) {
            t.out("lisätään energiaa == " + dsList.get(i).getEnergy());
            energySum += dsList.get(i).getEnergy();
        }
        
        int energyAverage = energySum / firstLoop;
        
        t.out("energyAverage == " + energyAverage);
        
        list.add(new Integer(energyAverage));
        
        // jäin tähän 23.09.2013
        
        return list;
        
    }
    
    public static int getAverageEnergy(List<DiningStats> dsList) {
        
        Calendar now = MyCalendar.getInstance();
        
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        
        int index = 0;
        int energySum = 0;
        int average = 0;
        for(DiningStats ds : dsList) {
            Calendar c = ds.getCalendarDate();
            if(now.compareTo(c) < 0) {
                break;
            }
//            t.out("päivä == " + ds.getDatePrefix());
            energySum += ds.getEnergy();
            index++;
        }
        average = energySum / index;
        
        
        return average;
        
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * Return eg. "Ma 23.09.2013"
     */
    public String getDatePrefix() {
        return MyCalendar.getWeekDay(calendarDate, true) + " " + date;
    }

    public int getDiningsCount() {
        return diningsCount;
    }

    public void setDiningsCount(int diningsCount) {
        this.diningsCount = diningsCount;
    }
    
    public int getVegetableGrams() {
        return vegetableGrams;
    }

    public void setVegetableGrams(int vegetableGrams) {
        this.vegetableGrams = vegetableGrams;
    }

    public Calendar getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(Calendar calendarDate) {
        this.calendarDate = calendarDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPriceString() {
        return Converter.roundedValue(price, 2);
    }
    
    
    
    
    
    
    
}
