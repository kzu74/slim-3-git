/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.commons.util.*;
import java.util.List;
import net.slimmer.dao.FoodDAO;
import net.slimmer.model.Food;
import net.slimmer.model.User;

/**
 *
 * @author Kaitsu
 */
public class FoodServlet extends BaseServlet {

    Trace t = new Trace(this.getClass());
    
    FoodDAO foodDAO = null;
    User user = null;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        foodDAO = new FoodDAO(getConnection(req));
        user = MySession.getUser(req);
        
        String act = Param.getString(req, "act", "none");
        
        if(act.equals("list") || act.equals("none")) {
            list(req, res);
        }
        else if(act.equals("edit")) {
            edit(req, res);
        }
        else if(act.equals("save")) {
            save(req, res);
        }
        else if(act.equals("delete")) {
            delete(req, res);
        }
        else if(act.equals("summary")) {
            summary(req, res);
        }
        
    }

    /**
     * TODO: if's elses!
     */
    protected void edit(HttpServletRequest req, HttpServletResponse res) {
        try {
            int id = Param.getInt(req, "id", 0);
            if(id > 0) {
                Food food = foodDAO.load(id);
                if(food != null) {
                    MyRequest.setFood(req, food);
                }
            }
            forward(req, res, "foodform.jsp");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void list(HttpServletRequest req, HttpServletResponse res) {
        
        String showAll = Param.getString(req, "showall", "all");
        t.out("showall ==  " + showAll);
        List<Food> list = null;
        try {
            if(showAll.equals("all")) {
                list = foodDAO.listAll();
            }
            else if(showAll.equals("own")) {
                list = foodDAO.listByOwner(user, "own");
            }
            else if(showAll.equals("others")) {
                list = foodDAO.listByOwner(user, "others");
            }
            req.setAttribute("foodlist", list);
            
            forward(req, res, "foodlist.jsp");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    } 
    
    protected void delete(HttpServletRequest req, HttpServletResponse res) {
        
        int id = Param.getInt(req, "id", 0);
        
        try {
            
            Food food = foodDAO.delete(id);
        
            if(food != null) {
                
                forward(req, res, food, " - Elintarvike poistettiin.", "foodsummary.jsp");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    } 
    
    
    protected void summary(HttpServletRequest req, HttpServletResponse res) {
        int id = Param.getInt(req, "id", 0);
        try {
            Food food = foodDAO.load(id);
            if(food.getId() > 0) {
                
                MyRequest.setFood(req, food);
                forward(req, res, "foodsummary.jsp");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    } 

    protected Food createWithFormData(HttpServletRequest req) {
        
        Food f = null;
        
        int id = Param.getInt(req, "id", 0);
        String name = Param.getString(req, "name", null);
        int energy = Param.getInt(req, "energy", 0);
        float protein = Param.getFloat(req, "protein", 0.0f);
        float carbs = Param.getFloat(req, "carbs", 0.0f);
        float fat = Param.getFloat(req, "fat", 0.0f);
        float fiber = Param.getFloat(req, "fiber", 0.0f);
        float natrium = Param.getFloat(req, "natrium", 0.0f);
        float price = Param.getFloat(req, "price", 0.0f);
        String type = Param.getString(req, "type", "undefined");
        boolean isPublic = Param.getBoolean(req, "isPublic"); // default false
        int userId = MySession.getUser(req).getId();
        
        
        f = new Food(id, name, energy, protein, carbs, fat, fiber, natrium, price, type, isPublic, userId);
        
        return f;
        
    }
    
    protected void save(HttpServletRequest req, HttpServletResponse res) {

        try {
            
            Food food = createWithFormData(req);
            
            if(food.getInvalidFieldMessages().isEmpty()) { // data is valid
            
                if(food.getId() == 0) { // case insert
                
                    food = foodDAO.insert(food); // suppose to have id after insert
                    
                    if(food.getId() > 0) { // successful insert
                    
                        forward(req, res, food, "- Uusi elintarvike tallennettiin.", "foodsummary.jsp");
                    }
                }
                else { // case update 
                    
                    boolean success = foodDAO.update(food);
                    
                    if(success) {
                        forward(req, res, food, "- Muutokset tallennettiin.", "foodsummary.jsp");
                    }
                }
            }
            else {
                
                food.printInvalidFieldMessages();
                setMessageList(req, food.getInvalidFieldMessages());
                forward(req, res, "foodform.jsp");
            }
        }
        catch(Exception e) {
        t.out("77");
            
            e.printStackTrace();
        }
    }
    
    protected void forward(HttpServletRequest req, HttpServletResponse res, Food food, String message, String page) 
        throws ServletException, IOException {
        MyRequest.setFood(req, food);
        addToMessageList(req, message);
        forward(req, res, page);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
}
