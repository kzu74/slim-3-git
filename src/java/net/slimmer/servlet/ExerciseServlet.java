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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import net.slimmer.dao.ExerciseDAO;
import net.slimmer.dao.SportDAO;
import net.slimmer.dao.UserDAO;
import net.slimmer.model.Exercise;
import net.slimmer.model.Sport;
import net.slimmer.model.User;

/**
 *
 * @author Kaitsu
 */
public class ExerciseServlet extends BaseServlet {

    Trace t = new Trace();
    
    
    ExerciseDAO exDAO = null;
    SportDAO sportDAO = null;
    UserDAO userDAO = null;
    private int userWeight = 80;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        exDAO = new ExerciseDAO(getConnection(req));
        sportDAO = new SportDAO(getConnection(req));
        userDAO = new UserDAO(getConnection(req));
        User k = null;
        try {
            k = userDAO.load(1);
            MySession.setUser(req, k);
        }
        catch(Exception e) {e.printStackTrace();}
        
        exDAO.setUserId(MySession.getUser(req).getId());
        
        userWeight = MySession.getUser(req).getWeight();
        
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
        } else if (act.equals("weeklydaylist")) {
            weeklyDayList(req, res);
        }
        
    }

    /**
     * TODO: if's elses!
     */
    protected void edit(HttpServletRequest req, HttpServletResponse res) {
        try {
            int id = Param.getInt(req, "id", 0);
            if(id > 0) {
                Exercise ex = exDAO.load(id);
                if(ex != null) {
                    ex.setUserWeight(userWeight);
                    MyRequest.setExercise(req, ex);
                    
                }
            }
            
            List<Sport> list = sportDAO.listAll();
            MyRequest.setSportList(req, list);
            
            forward(req, res, "exform.jsp");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void list(HttpServletRequest req, HttpServletResponse res) {
        
        try {
            Map<String, List<Exercise>> map = exDAO.listDailyExercises();
            
            // TODO: remove this quick fix by adding userId in the exercise db-table and weight in the user-table.
            for(String date : map.keySet()) {
                List<Exercise> list = map.get(date);

                for(Exercise ex : list) {
                    ex.setUserWeight(userWeight);
                }
            }
            
            
            MyRequest.setExerciseMap(req, map);
            forward(req, res, "exlist.jsp");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    } 
    
    protected void delete(HttpServletRequest req, HttpServletResponse res) {
        
        int id = Param.getInt(req, "id", 0);
        
        try {
            
            Exercise ex = exDAO.delete(id);
        
            if(ex != null) {
                
                forward(req, res, ex, " - Treeni poistettiin.", "exsummary.jsp");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    } 
    
    
    protected void summary(HttpServletRequest req, HttpServletResponse res) {
        int id = Param.getInt(req, "id", 0);
        try {
            Exercise ex = exDAO.load(id);
            if(ex.getId() > 0) {
                
                Sport sport = sportDAO.load(ex.getSportId());
                ex.setSport(sport);

                ex.setUserWeight(userWeight);

                MyRequest.setExercise(req, ex);
                forward(req, res, "exsummary.jsp");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    } 

    protected Exercise createWithFormData(HttpServletRequest req) {
        
        Exercise ex = null;
        
        int id = Param.getInt(req, "id", 0);
        String name = Param.getString(req, "name", null);
        String comments = Param.getString(req, "comments", "");
        Timestamp startTime = Param.getTimestamp(req, "date", "startTime");
        Timestamp endTime = Param.getTimestamp(req, "date", "endTime");
        int power = Param.getInt(req, "power", 6);
        int userWeight = 80;
        User u = MySession.getUser(req);
        if(u != null) {
            userWeight = u.getWeight();
        }
        
        int sportId = Param.getInt(req, "sportId", 0);
        
        
        ex = new Exercise(id, name, comments, startTime, endTime, power, userWeight, sportId);
        ex.setUserId(MySession.getUser(req).getId());
        
        return ex;
        
    }
    
    protected void save(HttpServletRequest req, HttpServletResponse res) {
        try {
            
            Exercise ex = createWithFormData(req);
            
            if(ex.getInvalidFieldMessages().isEmpty()) { // data is valid
            
                Sport sport = sportDAO.load(ex.getSportId());
                if(sport != null) {
                    ex.setSport(sport);
                }

                if(ex.getId() == 0) { // case insert
                
                    ex = exDAO.insert(ex); // suppose to have id after insert
                    
                    if(ex.getId() > 0) { // successful insert
                    
                        forward(req, res, ex, "- Uusi treeni tallennettiin.", "exsummary.jsp");
                    }
                }
                else { // case update 
                    
                    boolean success = exDAO.update(ex);
                    
                    if(success) {
                        forward(req, res, ex, "- Muutokset tallennettiin.", "exsummary.jsp");
                    }
                }
            }
            else {
                
                ex.printInvalidFieldMessages();
                setMessageList(req, ex.getInvalidFieldMessages());
                forward(req, res, "exform.jsp");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void weeklyDayList(HttpServletRequest req, HttpServletResponse res) {

        try {
            Map map = exDAO.listWeeklyDayStats();
            MyRequest.setExerciseStatsMap(req, map);
            forward(req, res, "exweeklydaylist.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    protected void forward(HttpServletRequest req, HttpServletResponse res, Exercise ex, String message, String page) 
        throws ServletException, IOException {
        MyRequest.setExercise(req, ex);
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
