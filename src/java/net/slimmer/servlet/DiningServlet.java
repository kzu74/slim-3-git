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
import java.sql.Connection;
//import com.sun.org.apache.bcel.internal.generic.D2F;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import net.slimmer.dao.DiningDAO;
import net.slimmer.dao.DiningStatsDAO;
import net.slimmer.dao.FoodDAO;
import net.slimmer.dao.ServingDAO;
import net.slimmer.model.Dining;
import net.slimmer.model.DiningStats;
import net.slimmer.model.Food;
import net.slimmer.model.Serving;
import net.slimmer.model.User;

/**
 *
 * @author Kaitsu
 */
public class DiningServlet extends BaseServlet {

    Trace t = new Trace(this.getClass());

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        String act = Param.getString(req, "act", "none");

        if (act.equals("list") || act.equals("none")) {
            list(req, res);
        } else if (act.equals("form")) {
            form(req, res);
        } else if (act.equals("save")) {
            save(req, res);
        } else if (act.equals("delete")) {
            delete(req, res);
        } else if (act.equals("deletefood")) {
            deleteFood(req, res);
        } else if (act.equals("summary")) {
            summary(req, res);
        } else if (act.equals("daysummary")) {
            daySummary(req, res);
        } else if (act.equals("daylist")) {
            dayList(req, res);
        } else if (act.equals("weeklydaylist")) {
            weeklyDayList(req, res);
        } else if (act.equals("copy")) {
            copy(req, res);
        }
        

    }

    /**
     * 10.08.2014
     */
    protected void copy(HttpServletRequest req, HttpServletResponse res) {

        Dining d2 = null;
        try {
            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            int id = Param.getInt(req, "id", 0);
            t.out("iideeee = " + id);
            if (id > 0) {
                Dining d1 = diningDAO.load(id);
                    t.out("d1 ================ " + d1);
                
                if (d1 != null) {
                    d2 = d1.cloneDining();
                    t.out("d2 ================ " + d2);
                    if(d2 != null) {
                        
                        d2.setDiningTime(Converter.toTimestamp(MyCalendar.getInstance()));
                        d2 = diningDAO.insert(d2);
                
                        if(d2.getId() > 0) {
                            
                            forward(req, res, d2, "- Uusi ruokailu tallennettiin.", "diningajaxform.jsp");
                            
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * TODO: if's elses!
     */
    protected void form(HttpServletRequest req, HttpServletResponse res) {

        try {
            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            int id = Param.getInt(req, "id", 0);
            Calendar dateCal = Param.getCalendarDateDotSeparated(req, "day");
            t.out("dateCal ============================================== " + dateCal);
            if (id > 0) {
                Dining dining = diningDAO.load(id);
                if (dining != null) {
                    MyRequest.setDining(req, dining);
                }
            }
            else if(dateCal != null) { // is valid dot separated date
                String date = Param.getString(req, "day");
                MyRequest.setDayString(req, date);
            }
            forward(req, res, "diningajaxform.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void editServing(HttpServletRequest req, HttpServletResponse res) {

        try {

            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            ServingDAO servingDAO = new ServingDAO(getConnection(req));
            List allServingsList = servingDAO.listAll();
            MyRequest.setAllFoodsList(req, allServingsList);

            int id = Param.getInt(req, "id", 0); // dining id
            
            if (id > 0) {
                Dining dining = diningDAO.load(id);
                if (dining != null) {
                    MyRequest.setDining(req, dining);
                }
            }
            forward(req, res, "diningservingform.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void list(HttpServletRequest req, HttpServletResponse res) {

        try {
            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            List<Dining> list = diningDAO.listAll();
            MyRequest.setDiningList(req, list);
            forward(req, res, "dininglist.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dayList(HttpServletRequest req, HttpServletResponse res) {

        try {
            DiningStatsDAO dsDAO = new DiningStatsDAO(getConnection(req));
            List<DiningStats> list = dsDAO.listDayStats();
            MyRequest.setDiningStatsList(req, list);
            forward(req, res, "diningdaylist.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void weeklyDayList(HttpServletRequest req, HttpServletResponse res) {

        try {
            DiningStatsDAO dsDAO = new DiningStatsDAO(getConnection(req));
            Map map = dsDAO.listWeeklyDayStats();
            MyRequest.setDiningStatsMap(req, map);
            forward(req, res, "diningweeklydaylist.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    protected void daySummary(HttpServletRequest req, HttpServletResponse res) {

        try {
            
            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            Calendar c = Param.getCalendarDateDotSeparated(req, "day");
            
            if(c == null) {
                c = MyCalendar.getInstance();
            }

            String date = Converter.toDateString(c);
            MyRequest.setDayString(req, date);
            int userId = MySession.getUserId(req);
            
            String weekDay = MyCalendar.getWeekDay(c, true);
            String prefixDay = weekDay + " " + date;
            List<Dining> list = diningDAO.listByDay(c, userId);
            MyRequest.setPrefixDayString(req, prefixDay);

            DiningStatsDAO dsDAO = new DiningStatsDAO(getConnection(req));
            int vegeGrams = dsDAO.getVegetableGrams(c);
            req.setAttribute("vegeGrams", new Integer(vegeGrams));

            c.add(Calendar.DAY_OF_YEAR, -1);
            String prevDay = Converter.toDateString(c);
            MyRequest.setPrevDayString(req, prevDay);
            
            c.add(Calendar.DAY_OF_YEAR, 2);
            String nextDay = Converter.toDateString(c);
            MyRequest.setNextDayString(req, nextDay);

            MyRequest.setDiningList(req, list);
            
            
            forward(req, res, "diningdaysummary.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse res) {

        int id = Param.getInt(req, "id", 0);

        try {

            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            Dining dining = diningDAO.delete(id);

            if (dining != null) {
                
            Calendar c = Param.getCalendarDateDotSeparated(req, "day");
            
            if(c == null) {
                c = MyCalendar.getInstance();
            }

            String date = Converter.toDateString(c);
            MyRequest.setDayString(req, date);
            int userId = MySession.getUserId(req);
            
            String weekDay = MyCalendar.getWeekDay(c, true);
            String prefixDay = weekDay + " " + date;
            List<Dining> list = diningDAO.listByDay(c, userId);
            MyRequest.setPrefixDayString(req, prefixDay);

            DiningStatsDAO dsDAO = new DiningStatsDAO(getConnection(req));
            int vegeGrams = dsDAO.getVegetableGrams(c);
            req.setAttribute("vegeGrams", new Integer(vegeGrams));

            c.add(Calendar.DAY_OF_YEAR, -1);
            String prevDay = Converter.toDateString(c);
            MyRequest.setPrevDayString(req, prevDay);
            
            c.add(Calendar.DAY_OF_YEAR, 2);
            String nextDay = Converter.toDateString(c);
            MyRequest.setNextDayString(req, nextDay);

            MyRequest.setDiningList(req, list);

                forward(req, res, dining, " - Ruokailu poistettiin: " + dining.getName(), "diningdaysummary.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void deleteFood(HttpServletRequest req, HttpServletResponse res) {

        int id = Param.getInt(req, "id", 0);
        
        try {

            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            FoodDAO foodDAO = new FoodDAO(getConnection(req));
            Dining dining = diningDAO.load(id);
            Food food = null;
            if (dining != null) {
                
                int foodId = Param.getInt(req, "foodId", 0); 
                
                if(foodId > 0) {
                    food = foodDAO.load(foodId);
                    if(food != null) {
                        dining = diningDAO.deleteFood(dining.getId(), foodId);
                    }
                }
            }

            forward(req, res, dining, " - Elintarvike poistettiin: " + food.getName(), "diningajaxform.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void summary(HttpServletRequest req, HttpServletResponse res) {
        int id = Param.getInt(req, "id", 0);
        try {
            DiningDAO diningDAO = new DiningDAO(getConnection(req));
            Dining dining = diningDAO.load(id);
            if (dining.getId() > 0) {

                MyRequest.setDining(req, dining);
                forward(req, res, "diningsummary.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Dining createWithFormData(HttpServletRequest req) {

        Dining dining = null;

        int id = Param.getInt(req, "id", 0);
        String name = Param.getString(req, "name", null);
        Timestamp diningStamp = Param.getTimestamp(req, "diningDate", "diningTime");
        String comments = Param.getString(req, "comments", "");
        
        List<Food> foodList = new ArrayList<Food>();
        //List<Integer> foodGramsList = new ArrayList<Integer>();

        boolean isFood = true;
        int i = 1;


        while (isFood) {

            int foodId = Param.getInt(req, "food" + i, 0);
            int foodGrams = Param.getInt(req, "foodgrams" + i, 0);

            if (foodId > 0 && foodGrams > 0) {

                try {
                    FoodDAO foodDAO = new FoodDAO(getConnection(req));
                    Food food = foodDAO.load(foodId);

                    if (food != null) {
                        food.setGrams(foodGrams);
                        foodList.add(food);
                        //foodGramsList.add(new Integer(foodGrams));
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
        for(Food fd : foodList) {
            energy += fd.getEnergyByGrams();
        }
        int userId = 1;
        User user = MySession.getUser(req);
        
        if(user != null) {
            userId = user.getId();
        }
        
        dining = new Dining(id, name, energy, diningStamp, comments, userId, foodList);

        return dining;

    }

    protected void save(HttpServletRequest req, HttpServletResponse res)  {

        try {
            Connection c = getConnection(req);
            ServingDAO servDAO = new ServingDAO(c);
            DiningDAO dineDAO = new DiningDAO(c);
            FoodDAO foodieDAO = new FoodDAO(c);
            t.out("Conn in SAVE == " + c);
            int id = Param.getInt(req, "id", 0); // dining id
            int userId = MySession.getUserId(req);
            String name = Param.getString(req, "name", "none"); // dining name
            Timestamp diningStamp = Param.getTimestamp(req, "diningDate", "diningTime");
            String foodOption = Param.getString(req, "foodOption", "none"); // servDAOing or food

            Dining dining = null;
            
            if(id == 0) { // case insert
                dining = new Dining();
            }
            else {
                t.out("Loading dining with ID == " + id + "in DiningServlet.save() update........");
                dining = dineDAO.load(id); // loads dining's foodList also
                t.out("Dining after load == " + dining);
            }
            dining.setName(name);
            dining.setDiningTime(diningStamp);
            dining.setUserId(userId);
            
            if (dining.getInvalidFieldMessages().isEmpty()) { // data is valid
/*
                if(foodOption.equals("serving")) { // previously weighed serving
                
                    int servingId = Param.getInt(req, "servingId", 0);
           
                    if(servingId > 0) {

                        Serving serving = serv.load(servingId);
                        List<Food> foodList = serving.getFoodList();
                        dining.addFoods(foodList);
                    }
                }
                */
//                String buttonAct = Param.getString(req, "buttonAct", "none");
                String saveAndAddButton = Param.getString(req, "saveAndAdd", null);
                String saveChangesButton = Param.getString(req, "saveChanges", null);
                
                if(saveAndAddButton != null) { // add serving or food and save
//                t.out("buttonAct == " + buttonAct);
//                if(buttonAct.equals("saveAndAdd")) {

                    if(foodOption.equals("servingGrams")) { // add serving

                        int servingGramsId = Param.getInt(req, "servingGramsId", 0);
                        int servingGrams = Param.getInt(req, "servingGrams", 0);

                        if(servingGramsId > 0) {

                            if(servingGrams > 0) {

                                Serving serving = servDAO.load(servingGramsId);
                                serving.setFoodGramsByPortion(servingGrams);
                                List<Food> foodList = serving.getFoodList();
                                dining.addFoods(foodList);
                            }
                        }
                    }
                    else if (foodOption.equals("food")) { // add food

                        int foodId = Param.getInt(req, "foodId", 0);
                        float foodGrams = Param.getFloat(req, "foodGrams", 0.0f);

                        if(foodId > 0) {
                            Food food = foodieDAO.load(foodId);
                            food.setGrams(foodGrams);
                            dining.addFood(food);
                        }
                    }
                }
                else if(saveChangesButton != null) { // update existing food list's grams and prices
                    
                    for(Food food : dining.getFoodList()) {
                        
                        String paramName = "foodGramsId" + food.getId();
                        float grams = Param.getFloat(req, paramName, 0);
                        String unitParamName = "foodUnitPriceId" + food.getId();
                        float unitPrice = Param.getFloat(req, unitParamName, -1.0f);
                        t.out("unitp savessa == " + unitPrice);
                        
                        if(grams > 0) {
                            food.setGrams(grams);
                        }
                        food.setUnitPrice(unitPrice);
                    }
                }
                
                if (dining.getId() == 0) { // case insert

                    dining = dineDAO.insert(dining); // suppose to have id after insert

                    if (dining.getId() > 0) { // successful insert

                        MyRequest.setDining(req, dining);
                        forward(req, res, dining, "- Uusi ruokailu tallennettiin.", "diningajaxform.jsp");
                    }
                } 
                else { // case update 

                    t.out("........else save.update CONN == " + c);
                    boolean success = dineDAO.update(dining);

                    if (success) { // successful update

                        forward(req, res, dining, "- Muutokset tallennettiin.", "diningajaxform.jsp");
                    }
                    else {
                        t.out("UPDATE EI TAAS TOIMINU.............");
                    }
                }
            }
            else {

                dining.printInvalidFieldMessages();
                setMessageList(req, dining.getInvalidFieldMessages());
                forward(req, res, dining, "", "diningajaxform.jsp");
            }
    
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void forward(HttpServletRequest req, HttpServletResponse res, Dining dining, String message, String page)
            throws ServletException, IOException {
        MyRequest.setDining(req, dining);
        addToMessageList(req, message);
        forward(req, res, page);
    }
/*
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
*/
}
