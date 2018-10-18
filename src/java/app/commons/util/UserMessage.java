/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.commons.util;

/**
 *
 * @author kaitsu
 */
public class UserMessage {
    
    private String language = "fi";
    
    public static String LOGIN_FAILED_FI = "Tarkista tunnukset";
    
    public static String LOGIN_FAILED_EN = "Check credentials";
    
    public UserMessage(){}
    
    public UserMessage(String lang) {
        language = lang;
    }
    
    public String getLoginFailed(){
        String s = LOGIN_FAILED_FI;
        if(getLanguage().equals("en")) s = LOGIN_FAILED_EN;
        return s;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }
    
    
    
}
