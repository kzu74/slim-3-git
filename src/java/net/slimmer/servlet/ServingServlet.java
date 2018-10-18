/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.servlet;
// jäin tähän 19.09.2013 klo 17.06 luo jsp-sivut! ctrl-c/v rules!!!!
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.commons.util.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import net.slimmer.dao.ServingDAO;
import net.slimmer.dao.FoodDAO;
import net.slimmer.model.Dining;
import net.slimmer.model.Serving;
import net.slimmer.model.Food;

/**
 *
 * @author Kaitsu
 */
public class ServingServlet extends BaseServlet {

    Trace t = new Trace(this.getClass());
    ServingDAO servingDAO = null;
    FoodDAO foodDAO = null;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        servingDAO = new ServingDAO(getConnection(req));
        foodDAO = new FoodDAO(getConnection(req));

        String act = Param.getString(req, "act", "none");

        if (act.equals("list") || act.equals("none")) {
            list(req, res);
        } else if (act.equals("listbydays")) {
            listByDaysOld(req, res);
        } else if (act.equals("edit")) {
            edit(req, res);
        } else if (act.equals("save")) {
            save(req, res);
        } else if (act.equals("delete")) {
            delete(req, res);
        } else if (act.equals("summary")) {
            summary(req, res);
        } else if (act.equals("deletefood")) {
            deleteFood(req, res);
        }

    }
    
    protected void deleteFood(HttpServletRequest req, HttpServletResponse res) {

        int id = Param.getInt(req, "id", 0);
        
        try {

            Serving s = servingDAO.load(id);
            Food food = null;
            if (s != null) {
                
                int foodId = Param.getInt(req, "foodId", 0); 
                
                if(foodId > 0) {
                    food = foodDAO.load(foodId);
                    if(food != null) {
                        s = servingDAO.deleteFood(s.getId(), foodId);
                    }
                }
            }

            forward(req, res, s, " - Ruoka poistettiin: " + food.getName(), "servingajaxform.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
     * TODO: if's elses!
     */
    protected void edit(HttpServletRequest req, HttpServletResponse res) {

        try {

            int id = Param.getInt(req, "id", 0);
            if (id > 0) {
                Serving serving = servingDAO.load(id);
                if (serving != null) {
                    MyRequest.setServing(req, serving);
                }
            }
            forward(req, res, "servingajaxform.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void list(HttpServletRequest req, HttpServletResponse res) {

        try {
            List<Serving> list = servingDAO.listAll();
            MyRequest.setServingList(req, list);
            forward(req, res, "servinglist.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void listByDaysOld(HttpServletRequest req, HttpServletResponse res) {

        try {
            List<Serving> list = servingDAO.listByDaysOld(10);
            MyRequest.setServingList(req, list);
            forward(req, res, "servinglist.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse res) {

        int id = Param.getInt(req, "id", 0);

        try {

            Serving serving = servingDAO.delete(id);

            if (serving != null) {

                forward(req, res, serving, " - Annos poistettiin.", "servingsummary.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void summary(HttpServletRequest req, HttpServletResponse res) {
        
        int id = Param.getInt(req, "id", 0);
        try {
            Serving serving = servingDAO.load(id);
            if (serving.getId() > 0) {

                MyRequest.setServing(req, serving);
                forward(req, res, "servingsummary.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    
    protected Serving createWithFormData(HttpServletRequest req) {

        Serving serving = null;

        int id = Param.getInt(req, "id", 0);
        String name = Param.getString(req, "name", null);
        Timestamp servingStamp = Param.getTimestamp(req, "servingDate", "servingTime");
        String comments = Param.getString(req, "comments", "");
        int userId = MySession.getUser(req).getId();
        t.out("u id == " + userId);
        List<Food> foodList = new ArrayList<Food>();
        List<Integer> foodGramsList = new ArrayList<Integer>();

        boolean isFood = true;
        int i = 1;


        while (isFood) {

            int foodId = Param.getInt(req, "food" + i, 0);
            int foodGrams = Param.getInt(req, "foodgrams" + i, 0);

            if (foodId > 0 && foodGrams > 0) {

                try {

                    Food food = foodDAO.load(foodId);

                    if (food != null && foodGrams > 0) {
                        foodList.add(food);
                        food.setGrams(foodGrams);
                    } else {
                        // TODO: food not found by id!!!
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
            else {
                isFood = false;
            }
            i++;
            if(i > 100) break;
        }
        int energy = 0;
        for(Food f : foodList) {
            energy += f.getEnergyByGrams();
        }

        serving = new Serving(id, name, energy, servingStamp, comments, userId, foodList);

        return serving;

    }
*/
    /*
    protected void saveOld(HttpServletRequest req, HttpServletResponse res) {

        try {

            Serving serving = createWithFormData(req);

            if (serving.getInvalidFieldMessages().isEmpty()) { // data is valid

                if (serving.getId() == 0) { // case insert

                    serving = servingDAO.insert(serving); // suppose to have id after insert

                    if (serving.getId() > 0) { // successful insert

                        forward(req, res, serving, "- Uusi annos tallennettiin.", "servingsummary.jsp");
                    }
                } else { // case update 

                    boolean success = servingDAO.update(serving);

                    if (success) { // successful update
                        
                        forward(req, res, serving, "- Muutokset tallennettiin.", "servingsummary.jsp");
                    }
                }
            } 
            else {

                serving.printInvalidFieldMessages();
                setMessageList(req, serving.getInvalidFieldMessages());
                forward(req, res, "servingform.jsp");
            }
        } catch (Exception e) {
            t.out("77");

            e.printStackTrace();
        }
    }
*/
    
    protected void save(HttpServletRequest req, HttpServletResponse res) {

        try {

            int id = Param.getInt(req, "id", 0); // serving id
            String name = Param.getString(req, "name", "none"); // serving name

            Serving serving = null;
            
            if(id == 0) { // case insert
                serving = new Serving();
                serving.setUserId(MySession.getUser(req).getId());
            }
            else {
                serving = servingDAO.load(id); // loads serving's foodList also
            }
            serving.setName(name);
            
            if (serving.getInvalidFieldMessages().isEmpty()) { // data is valid

                String saveAndAddButton = Param.getString(req, "saveAndAdd", null);
                String saveChangesButton = Param.getString(req, "saveChanges", null);

                if(saveAndAddButton != null) { // case add food
                
                    int foodId = Param.getInt(req, "foodId", 0);
                    int foodGrams = Param.getInt(req, "foodGrams", 0);

                    if(foodId > 0) {
                        Food food = foodDAO.load(foodId);
                        food.setGrams(foodGrams);
                        serving.addFood(food);
                    }
                }
                else if(saveChangesButton != null) { // update existing food list's grams and prices
                                        
                    for(Food food : serving.getFoodList()) {
                        
                        String paramName = "foodGramsId" + food.getId();
                        float grams = Param.getFloat(req, paramName, 0.0f);
                        String unitParamName = "foodUnitPriceId" + food.getId();
                        float unitPrice = Param.getFloat(req, unitParamName, 0.0f);
                        
                        if(grams > 0) {
                            food.setGrams(grams);
                        }
                        food.setUnitPrice(unitPrice);
                    }
                }
                
                if (serving.getId() == 0) { // case insert

                    serving = servingDAO.insert(serving); // suppose to have id after insert

                    if (serving.getId() > 0) { // successful insert

                        forward(req, res, serving, "- Uusi annos tallennettiin.", "servingajaxform.jsp");
                    }
                } 
                else { // case update 

                    boolean success = servingDAO.update(serving);

                    if (success) { // successful update

                        forward(req, res, serving, "- Muutokset tallennettiin.", "servingajaxform.jsp");
                    }
                }
            }
            else {

                serving.printInvalidFieldMessages();
                setMessageList(req, serving.getInvalidFieldMessages());
                forward(req, res, "servingajaxform.jsp");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void forward(HttpServletRequest req, HttpServletResponse res, Serving serving, String message, String page)
            throws ServletException, IOException {
        MyRequest.setServing(req, serving);
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
