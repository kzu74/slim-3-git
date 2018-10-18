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
public class Dining extends Model implements Cloneable {
    
    private String name = "";
    private int energy = 0;
    private Timestamp diningTime = null;
    private String comments = "";
    private int userId = 0;
    
    private List<Food> foodList = new ArrayList<Food>();
    private List<Integer> handledFoodIdsList = new ArrayList<Integer>();
        
    private static Trace t = new Trace();
        
    public Dining() {}
    
    public Dining(int id, String name, int energy, Timestamp diningTime, String comments, int userId, List<Food> foodList) {
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.diningTime = diningTime;
        this.comments = comments;
        this.userId = userId;
        this.foodList = foodList;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        Object o = super.clone();
        
        return o;
    }
    
    /**
     * Clone dining object this, set it's id to zero. If clone is failed, return null.
     */
    public Dining cloneDining() {
        
        Dining d = new Dining();
        try {
            d = (Dining)clone();
            d.setId(0);
        }
        catch(CloneNotSupportedException e) {
            e.printStackTrace();
            d = null;
        }
        return d;
    }
    
    /**
     * Dining price based on food's unit price (eg. price / kg in Europe).
     * 
     */
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
    
    public int getVegetableGrams() {
        int g = 0;
        for(Food food : foodList) {
            if(food.isVegetable()) {
                g+= food.getGrams();
            }
        }
        return g;
    }

    public float getNatrium() {
        
        float g = 0.0f;
        
        for(Food food : foodList) {
            g+= food.getNatriumByGrams();
            
        }
        
        return g;
    }
    
    public String getNatriumString() {
        return Converter.roundedValue(getNatrium(), 2);
    }
    
    public float getFiber() {
        
        float g = 0.0f;
        
        for(Food food : foodList) {
            g+= food.getFiberByGrams();
            
        }
        
        return g;
    }
    
    public String getFiberString() {
        return Converter.roundedValue(getFiber(), 1);
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
    
    public float getFoodGrams(int index) {
                
        if(foodList.size() > index) {
        
            return foodList.get(index).getGrams();
        }
        
        return -1;
    }
    
    public void addFood(Food food) {
        if(!isAlreadyInFoodList(food)) {
            foodList.add(food);
        }
        else {
            addFoodGrams(food);
        }
    }
    
    public void addFoodGrams(Food food) {
        for(Food f : foodList) {
            if(f.getId() == food.getId()) {
                float grams = f.getGrams() + food.getGrams();
                f.setGrams(grams);
            }
        }
    }
    
    // jäin tähän 5.10.2013 klo 19:39
    public void addFoods(List<Food> addList) {
        
        if(this.getId() == 0) {
            foodList.addAll(addList);
            return;
        }

        for(Food food : addList) {

            if(!isAlreadyInFoodList(food)) {
                foodList.add(food);
            }
            else if(!isAlreadyHandled(food.getId())){
                addFoodGrams(food);
                addHandledFoodId(food.getId());
            }
        }
    }
    
    public void addHandledFoodId(int id) {
        handledFoodIdsList.add(new Integer(id));
    }
    
    public boolean isAlreadyHandled(int id) {
        for(int foodId : handledFoodIdsList) {
            if(foodId == id) return true;
        }
        return false;
    }
    
    public boolean isAlreadyInFoodList(Food food) {
        for(Food f : foodList) {
            if(f.getId() == food.getId()) return true;
        }
        return false;
    }
    
    /**
     * Calculates foodList's energy sum based on foodList's eated grams.
     */
    public int getEnergy() {
        
        float total = 0.0f;
        for(Food food : foodList) {
            total += food.getEnergyByGrams();
        }
        return Converter.roundUp(total);
    }
    
    /**
     * Calculates given diningList's calories == energy sum.
     */
    public static int getEnergySum(List<Dining> diningList) {
        
        int n = 0;
        
        for(Dining dining : diningList) {
            n+= dining.getEnergy();
        }
        
        return n;
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
    
    public static float getProteinSum(List<Dining> diningList) {
        
        float n = 0;
        
        for(Dining dining : diningList) {
            
            List<Food> foodList = dining.getFoodList();
            
            for(Food food : foodList) {
                
                n += food.getProteinByGrams();
            }
        }
        
        return n;
    }
    
    public static String getProteinSumString(List<Dining> diningList) {
        float n = getProteinSum(diningList);
        return Converter.roundedValue(n, 1);
    }
    
    public static float getPriceSum(List<Dining> diningList) {
        
        float n = 0;
        
        for(Dining dining : diningList) {
            
            List<Food> foodList = dining.getFoodList();
            
            for(Food food : foodList) {
                
                n += food.getServingPrice();
            }
        }
        
        return n;
    }
    
    public static String getPriceSumString(List<Dining> diningList) {
        float n = getPriceSum(diningList);
        return Converter.roundedValue(n, 2);
    }

    public static float getCarbsSum(List<Dining> diningList) {
        
        float n = 0;
        
        for(Dining dining : diningList) {
            
            List<Food> foodList = dining.getFoodList();
            
            for(Food food : foodList) {
                
                n += food.getCarbsByGrams();
            }
        }
        
        return n;
    }
    
    public static String getCarbsSumString(List<Dining> diningList) {
        float n = getCarbsSum(diningList);
        return Converter.roundedValue(n, 1);
    }
    
    public static float getFatSum(List<Dining> diningList) {
        
        float n = 0;
        
        for(Dining dining : diningList) {
            
            List<Food> foodList = dining.getFoodList();
            
            for(Food food : foodList) {
                
                n += food.getFatByGrams();
            }
        }
        
        return n;
    }
    
    public static String getFatSumString(List<Dining> diningList) {
        float n = getFatSum(diningList);
        return Converter.roundedValue(n, 1);
    }

    public static float getNatriumSum(List<Dining> diningList) {
        
        float n = 0;
        
        for(Dining dining : diningList) {
            
            List<Food> foodList = dining.getFoodList();
            
            for(Food food : foodList) {
                
                n += food.getNatriumByGrams();
            }
        }
        t.out("palautetaan nat == " + n);
        
        return n;
    }
    
    public static String getNatriumSumString(List<Dining> diningList) {
        float n = getNatriumSum(diningList);
        return Converter.roundedValue(n, 2);
    }
    
    public static float getFiberSum(List<Dining> diningList) {
        
        float n = 0;
        
        for(Dining dining : diningList) {
            
            List<Food> foodList = dining.getFoodList();
            
            for(Food food : foodList) {
                
                n += food.getFiberByGrams();
            }
        }
        
        return n;
    }
    
    public static String getFiberSumString(List<Dining> diningList) {
        float n = getFiberSum(diningList);
        return Converter.roundedValue(n, 1);
    }
    
    
    public static int getVegetablesSum(List<Dining> diningList) {
        
        int n = 0;
        
        for(Dining dining : diningList) {
            n += dining.getVegetableGrams();
        }
        
        return n;
    }

    public static String getVegetablesSumString(List<Dining> diningList) {
        float n = getVegetablesSum(diningList);
        return Converter.roundedValue(n, 1);
    }
    
    /**
     * Eg. 18:15
     */
    public String getTime() {
        return Converter.toTimeString(diningTime);
    }
    /**
     * Eg. 19.08.2013
     */
    public String getDate() {
        return Converter.toDateString(diningTime);
    }
    
    public float getFoodGramsTotal() {
        
        float total = 0;
        
        for(Food food : foodList) {
            total += food.getGrams();
        }
        return total;
    }

    public String getFoodGramsTotalString() {
        return Converter.roundedValue(getFoodGramsTotal(), 0);
    }
    
    public List<String> getInvalidFieldMessages() {
        
        //t.out("name == " + name);
        
        List<String> fields = new ArrayList<String>();
        
        if(!Validator.isValidName(name)) fields.add("- Nimen tulee olla 2 - 100 merkkiä pitkä.");
        if(!Validator.isValidTimestamp(diningTime)) fields.add("- Tarkista päivämäärä ja kellonaika.");
        
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
    
    /*
    public int getEnergy() {
        return energy;
    }
*/
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Timestamp getDiningTime() {
        return diningTime;
    }

    public void setDiningTime(Timestamp diningTime) {
        this.diningTime = diningTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
