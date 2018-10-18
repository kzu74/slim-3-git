package net.slimmer.servlet;

import app.commons.util.Param;
import app.commons.util.Trace;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.slimmer.dao.FoodDAO;
import net.slimmer.dao.ServingDAO;
import net.slimmer.model.Food;
import net.slimmer.model.Serving;

 
public class AjaxServlet extends BaseServlet {
    
    Trace t = new Trace(getClass());
    
    public AjaxServlet() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        t.out("tultiin doPostiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiin...................");
        
        String act = Param.getString(request, "act", "serving");
        
        if(act.equals("serving")) {
            doServing(request, response);
        }
        else if(act.equals("food")) {
            doFood(request, response);
        }
    }
    
    
    
    
   
    protected void doServing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setCharacterEncoding("UTF-8");

        ServingDAO sDAO = new ServingDAO(getConnection(request));
        List<Serving> servingList = null;
        Trace t = new Trace();
        
        String term = Param.getString(request, "data", "xyz");

        JSONArray jsa = new JSONArray();

        try {
            servingList = sDAO.listByPartOfName(term);
            t.out("Serving LIST LENGTH == " + servingList.size());
            for(Serving serving : servingList) {

                JSONObject jso = new JSONObject();

                jso.put("id", new Integer(serving.getId()));
                jso.put("name", serving.getName());

                jsa.add(jso);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        t.out(jsa.toString());

        out.println(jsa.toString());

        out.close();
        
    }
    
    protected void doFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
       // response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
        FoodDAO fDAO = new FoodDAO(getConnection(request));
        List<Food> foodList = null;
        Trace t = new Trace();
        String term = request.getParameter("data");
        JSONArray jsa = new JSONArray();
        try {
            foodList = fDAO.listByPartOfName(term);
            t.out("FOOD LIST LENGTH == " + foodList.size());
            for(Food food : foodList) {
                
                JSONObject jso = new JSONObject();
                jso.put("id", new Integer(food.getId()));
                jso.put("name", food.getName());
                
                jsa.add(jso);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
       
        t.out(jsa.toString());
        out.println(jsa.toString());
        
        out.close();
    
    } 
    
    protected void doServingAndFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        
        FoodDAO fDAO = new FoodDAO(getConnection(request));
        List<Food> foodList = null;
        Trace t = new Trace();
        String term = request.getParameter("data");
        JSONArray jsa = new JSONArray();
        try {
            foodList = fDAO.listByPartOfName(term);
            t.out("FOOD LIST LENGTH == " + foodList.size());
            for(Food food : foodList) {
                
                JSONObject jso = new JSONObject();
                jso.put("id", new Integer(food.getId()));
                jso.put("name", food.getName());
                
                jsa.add(jso);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
       
        t.out(jsa.toString());
        out.println(jsa.toString());
        
        out.close();
    
    } 
    
}