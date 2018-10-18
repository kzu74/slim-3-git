/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.model;

import app.commons.util.Trace;
import app.commons.util.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kaitsu
 */
public class Model {
    
    protected int id = 0;
    
    protected Trace trace = new Trace(this.getClass());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void printInvalidFieldMessages() {
        
        List<String> list = getInvalidFieldMessages();
        
        for(String s : list) {
            trace.out(s);
        }
    }
    
    
    public List<String> getInvalidFieldMessages() {
                
        return null;
    }
    
    
    
}
