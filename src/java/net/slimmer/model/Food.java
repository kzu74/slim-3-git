/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

import app.commons.util.Converter;
import app.commons.util.Trace;
import app.commons.util.Validator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kaitsu
 */
public class Food extends Model {
    
    private String name = "";
    private int energy = 0; // food energy in kcal / 100g
    private float protein = 0.0f; // all these are / 100g
    private float carbs = 0.0f;
    private float fat = 0.0f;
    private float fiber = 0.0f;
    private float natrium = 0.0f;
    //private float servingPrice = 0.0f; // price based on grams eaten
    private float unitPrice = 0.0f; // price / 1kg
    
    private String type = "undefined"; // "vegetable", "meat", "fish" etc.
    private boolean isPublic = false;
    private int userId = 0;
    
    
    
    private static Map<String, String> foodTypeMap = new LinkedHashMap<String, String>();
    
    private float grams = 0; // not in db, used for tieing eaten grams to this food.
    
    
    private Trace t = new Trace(this.getClass());
    
    public Food() {}
    
    public Food(int id, String name, int energy,  float protein, float carbs,  float fat, 
            float fiber, float natrium, float unitPrice, String type, boolean isPublic, int userId) {
        
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.fiber = fiber;
        this.natrium = natrium;
        this.unitPrice = unitPrice;
        this.type = type;
        this.isPublic = isPublic;
        this.userId = userId;
        
        setFoodTypeMap();
    }
    
    public boolean isVegetable() {
        if(type.equals("vegetable")) {
            return true;
        }
        return false;
    }
    
    public float getServingPrice() {
        float factor = (float)grams / 1000;
        return factor * unitPrice;
    }
    
    public static float getServingPrice(int grams, float unittPrice) {
        float factor = (float)grams / 1000;
        return factor * unittPrice;
    }
    
    public static Map<String, String> getFoodTypeMap() {
        if(foodTypeMap.isEmpty()) {
            setFoodTypeMap();
        }
        return foodTypeMap;
    }
    
    public static void setFoodTypeMap() {
        
        if(foodTypeMap.isEmpty()) {
            foodTypeMap.put("vegetable", "Kasvis");
            foodTypeMap.put("vegetable-oil", "Kasviöljy");
            foodTypeMap.put("root-vegetable", "Juures");
            foodTypeMap.put("fruit", "Hedelmä");
            foodTypeMap.put("berry", "Marja");
            foodTypeMap.put("nut", "Pähkinä");
            foodTypeMap.put("seed", "Siemen");
            foodTypeMap.put("grain", "Vilja");
            foodTypeMap.put("dairy", "Maitotuotteet");
            foodTypeMap.put("meat", "Liha");
            foodTypeMap.put("fish", "Kala");
            foodTypeMap.put("chicken", "Kana");
            foodTypeMap.put("egg", "Kananmuna");
            foodTypeMap.put("coffee", "Kahvi");
            foodTypeMap.put("tea", "Tee");
            foodTypeMap.put("choklad", "Suklaa");
            foodTypeMap.put("candy", "Karkki");
            foodTypeMap.put("ketchup", "Ketsuppi");
            foodTypeMap.put("spice", "Mauste");
            foodTypeMap.put("protein-powder", "Proteiinijauhe");
            foodTypeMap.put("other", "Muu");
        }
    }
    
    public int getEnergyByGrams() {
        
        float total = (float)grams * energy / 100;
        
        return Converter.roundUp(total);
        
    }
    
    public float getProteinByGrams() {
        float total = grams * protein / 100;
        return total;
    }
    public float getCarbsByGrams() {
        float total = grams * carbs / 100;
        return total;
    }
    public float getFatByGrams() {
        float total = grams * fat / 100;
        return total;
    }
    public float getNatriumByGrams() {
        float total = (float)(grams * natrium / 100);
        return total;
    }
    public float getFiberByGrams() {
        float total = grams * fiber / 100;
        return total;
    }
    
    public String getCarbsByGramsString() {
        return Converter.roundedValue(getCarbsByGrams(), 1);
    }
    public String getFatByGramsString() {
        return Converter.roundedValue(getFatByGrams(), 1);
    } 
    public String getProteinByGramsString() {
        return Converter.roundedValue(getProteinByGrams(), 1);
    }
    public String getNatriumByGramsString() {
        return Converter.roundedValue(getNatriumByGrams(), 2);
    }
    public String getFiberByGramsString() {
        return Converter.roundedValue(getFiberByGrams(), 1);
    }
    
    
    
    /**
     * Eli ideana ois laittaa talteen ne käyttäjän syöttämät arvot tänne, jotta ne voidaan
     * laittaa takaisin nettisivulle, niin että käyttäjä näkee mitä hän on syöttänyt. Jos
     * esim vain pilkku ym. erotus kirjain on mennyt väärin, sen voi helposti korjata.
     * 
     * Ja kun Food-olio kerran on koodattu kuntoon niin, että se osaa tarkistaa omat
     * arvonsa Validator-olion avulla, niin ctrl-oliot pysyy yksinkertaisempina.
     * 
     * Jotenkin pitäis kuitenkin ottaa vielä huomioon monikielisyys, tai sitten vasta
     * tulevaisuudessa.
     * 
     * En tiiä onko hyötyy kattoo jostain props-filestä noita erikseen... Ei ainakaan vielä.
     */
    public List<String> getInvalidFieldMessages() {
        
        //t.out("name == " + name);
        
        List<String> fields = new ArrayList<String>();
        
        if(!Validator.isValidName(name)) fields.add("- Nimen tulee olla vähintään 2 merkkiä pitkä.");
        // if(!Validator.isValidInteger) // jäin tähän 26.1.2014
        
        //t.out("fields leng == " + fields.size());
        
        return fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getProteinString() {
        return Converter.roundedValue(getProtein(), 1);
    }

    public String getCarbsString() {
        return Converter.roundedValue(getCarbs(), 1);
    }
    
    public String getFatString() {
        return Converter.roundedValue(getFat(), 1);
    }

    public String getNatriumString() {
        String s = Converter.roundedValue(getNatrium(), 2);
        return s;
    }
    
    public String getFiberString() {
        return Converter.roundedValue(getFiber(), 1);
    }
    
    public String getUnitPriceString() {
        return Converter.roundedValue(getUnitPrice(), 2);
    }

    public String getServingPriceString() {
        return Converter.roundedValue(getServingPrice(), 2);
    }
    
    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getNatrium() {
        return natrium;
    }

    public void setNatrium(float natrium) {
        this.natrium = natrium;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }

    public String getGramsString() {
        return Converter.roundedValue(grams, 1);
    }
    
    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float price) {
        this.unitPrice = price;
    }

    public void setType(String t) {
        this.type = t;
    }
    
    public String getType() {
        return type;
    }

    public void setIsPublic(boolean p) {
        this.isPublic = p;
    }
    
    public boolean getIsPublic() {
        return isPublic;
    }
    
    public void setUserId(int i) {
        this.userId = i;
    }

    public int getUserId() {
        return userId;
    }

}
