/*
 * HbrUser.java
 *
 * Created on 16. hein�kuuta 2011, 17:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.slimmer.model;

import java.util.ArrayList;
import java.util.List;
import app.commons.util.Validator;
import java.sql.Date;
import app.commons.util.Converter;

import java.util.Calendar;

public class User extends Model {
    
    public User() {}
    
    private String firstName = "";
    private String lastName = "";
    
    private int height = 172;
    private int weight = 80;
    
    private String email = "";
    private String phone = "";
    private String password = "";
    
    private java.sql.Date birthDay = null;

    private String street = "";
    private String zip = "";
    private String city = "";

    private String gender = "male"; // "male" / "female"
    private String userGroup = "";
    
    private boolean validSession = false; // http session check
   
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    
    /**
     * firstName + " " + lastName, eg. John Smith.
     */
    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
    
    public String getUserGroupText() {
        if(getUserGroup() != null && getUserGroup().equals("employee")) return "Ty�ntekij�";
        else return "Asiakas";
    }
    
    public List<String> getInvalidFieldMessages() {
        
        List<String> fields = new ArrayList<String>();
        /**
        if(!Validator.isValidName(getFirstName())) fields.add("- Etunimen tulee olla vähintään 2 merkkiä pitkä.");
        if(!Validator.isValidName(getLastName())) fields.add("- Sukunimen tulee olla vähintään 2 merkkiä pitkä.");
        if(!Validator.isValidGender(getGender())) fields.add("- Valitse mies tai nainen.");
        if(!Validator.isValidEmail(getEmail())) fields.add("- Tarkista email.");
        if(!Validator.isValidPhone(getPhone())) fields.add("- Tarkista puhelinnumero");
        if(!Validator.isValidPassword(getPassword())) fields.add("- Tarkista salasana.");
        if(!Validator.isValidBirthDay(getBirthDayCalendar())) fields.add("- Tarkista syntymäaika.");
        if(!Validator.isValidStreet(getStreet())) fields.add("- Tarkista katuosoite.");
        if(!Validator.isValidZipCode(getZip())) fields.add("- Tarkista postinumero.");
        if(!Validator.isValidCity(getCity())) fields.add("- Tarkista kaupunki.");
        if(!Validator.isValidName(getUserGroup())) fields.add("- Tarkista käyttäjäryhmä.");
        */
        return fields;
    }
    
    public List<String> getInvalidFieldNames() {
        
        List<String> fields = new ArrayList<String>();
        
        if(!Validator.isValidName(getFirstName())) fields.add("firstName");
        if(!Validator.isValidName(getLastName())) fields.add("lastName");
        if(!Validator.isValidEmail(getEmail())) fields.add("email");
        if(!Validator.isValidPhone(getPhone())) fields.add("phone");
        if(!Validator.isValidPassword(getPassword())) fields.add("password");
        if(!Validator.isValidName(getUserGroup())) fields.add("userGroup");
        
        
        return fields;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Calendar getBirthDayCalendar() {
        if(birthDay == null) return null;
        return Converter.toCalendar(birthDay);
    }
    /**
     * Receives calendar birth and converts and sets it to java.sql.Date birthDay
     */
    public void setBirthDay(Calendar birth) {
        if(birth != null) {
            setBirthDay(Converter.toDate(birth));
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthDay() {
        return birthDay;
    }
    
    public String getBirthDayString() {
        String s = "";
        if(birthDay != null) {
            s = Converter.toDateString(getBirthDayCalendar());
        }
        return s;
    }

    public void setBirthDay(java.sql.Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean validSession() {
        return validSession;
    }

    public void setValidSession(boolean validSession) {
        this.validSession = validSession;
    }
    
    
    



    
    
    
    
    
}
