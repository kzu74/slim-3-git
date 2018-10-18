/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

/**
 *
 * @author Kaitsu
 */
public class Sport extends Model {
    
    private String name = "";
    private int minCalories = 0;
    private int maxCalories = 0;
    private String abbreviation = "";
    
    public static final int PERSON_WEIGHT = 70; // calories are based on 70kg person's weight.
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(int minCalories) {
        this.minCalories = minCalories;
    }

    public int getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(int maxCalories) {
        this.maxCalories = maxCalories;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
    
    
    
    
}
