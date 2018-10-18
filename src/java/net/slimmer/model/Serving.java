/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

import app.commons.util.Converter;
import app.commons.util.Trace;
import app.commons.util.Validator;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kaitsu
 */
public class Serving extends Model {
    
    private String name = "";
    private int energy = 0;
    //private float weighedGrams = 0;
    private Timestamp created = null;
    private String comments = "";
    private int userId = 0;
    //private float price = 0;
    //private int weightGrams = 0; vai tulisko laskea foodGramsseista?
    
    private List<Food> foodList = new ArrayList<Food>(); // same order than foodGramsList
    
    private Trace t = new Trace(this.getClass());
    
    public Serving() {}
    
    public Serving(int id, String name, int energy, Timestamp created, String comments, int userId, List<Food> foodList) {
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.created = created;
        this.comments = comments;
        this.userId = userId;
        this.foodList = foodList;
    }
    
    public float getPrice() {
        
        float price = 0.0f;
        
        for(Food food : foodList) {
            price += food.getServingPrice();
        }
        
        return price;
    }
    
    
    public String getPriceString() {
        return Converter.roundedValue(getPrice(), 2);
    }
    
    /**
     * Change foodList's foodGrams values by factor portionGrams / gramsTotal.
     */
    public void setFoodGramsByPortion(int portionGrams) {
        
        float factor = (float)portionGrams / getFoodGramsTotal();
        
        for(Food food : foodList) {
            float grams = (float)factor * food.getGrams();
            food.setGrams(grams);
        }
    }
    
    
    /**
     * Returns food object from foodList by index number or null if there is none.
     */
    public Food getFood(int foodListIndex) {
        
        int compare = foodListIndex + 1;
        
        if(foodList.size() >= compare) {
        
            return foodList.get(foodListIndex);
        }
        
        return null;
        
    }
    
    public void addFood(Food food) {
        foodList.add(food);
    }
    
    public int getEnergy() {
        
        int total = 0;
        for(Food food : foodList) {
            total += food.getEnergyByGrams();
        }
        return total;
    }
        
    public float getFoodGramsTotal() {
        
        float total = 0.0f;
        
        for(Food food : foodList) {
            total += food.getGrams();
        }
        return total;
    }

    public String getFoodGramsTotalString() {
        return Converter.roundedValue(getFoodGramsTotal(), 1);
    }
    
    
    public float getProtein() {
        
        float n = 0;
        
        for(Food food : foodList) {
            n += food.getProteinByGrams();
        }
        
        return n;
    }
    
    public String getProteinString() {
        return Converter.roundedValue(getProtein(), 1);
    }
    
    public float getCarbs() {
        
        float n = 0;
        
        for(Food food : foodList) {
            n += food.getCarbsByGrams();
            //t.out("Food name == " + food.getName() + " ja CARBS byGrams == " + food.getCarbsByGrams());
        }
        
        return n;
    }
    
    public String getCarbsString() {
        return Converter.roundedValue(getCarbs(), 1);
    }
    
    public float getFat() {
        
        float n = 0;
        
        for(Food food : foodList) {
           // t.out("food name = " + food.getName() + " ja FAT == " + food.getFat());
            n += food.getFatByGrams();
        }
        
        return n;
    }
    
    public String getFatString() {
        return Converter.roundedValue(getFat(), 1);
    }
    
    
    public List<String> getInvalidFieldMessages() {
        
        t.out("name == " + name);
        
        List<String> fields = new ArrayList<String>();
        
        if(!Validator.isValidName(name)) fields.add("- Nimen tulee olla vähintään 2 merkkiä pitkä.");
        
        //t.out("fields leng == " + fields.size());
        
        return fields;
    }
    
    /* getters and setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
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

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }



    
    
}
