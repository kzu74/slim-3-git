/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.dao;

import app.commons.util.MyCalendar;
import app.commons.util.Trace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.slimmer.model.Dining;
import net.slimmer.model.Food;

/**
 *
 * @author Kaitsu
 */
public class DiningDAO extends DAO {

    Trace t = new Trace();
    
    private static String loadSQL = "select * from dining where id = ?";
    
    private static String insertSQL = "insert into dining (name, energy, diningTime, comments, userId) values (?,?,?,?,?)";

    private static String updateSQL = "update dining set name = ?, energy = ?, diningTime = ?, comments = ?, userId=? where id = ?";
    
    private static String deleteSQL = "delete from dining where id = ?";
    
    private static String listAllSQL = "select * from dining";
    
    public DiningDAO(Connection c) {
        super(c);
    }

    /**
     * Binds insertSQL or updateSQL to user data. If users id > 0, binds one
     * more "?" to user.id.
     */
    public static void bind(PreparedStatement stmt, Dining dining) throws Exception {

        stmt.setString(1, dining.getName());
        stmt.setInt(2, dining.getEnergy());
        stmt.setTimestamp(3, dining.getDiningTime());
        stmt.setString(4, dining.getComments());
        stmt.setInt(5, dining.getUserId());
        
        if (dining.getId() > 0) {
            stmt.setInt(6, dining.getId());
        }

    }
    
    public static void bind(ResultSet rset, Dining dining) throws Exception {

        dining.setId(rset.getInt("id"));
        dining.setName(rset.getString("name"));
        dining.setEnergy(rset.getInt("energy"));
        dining.setDiningTime(rset.getTimestamp("diningTime"));
        dining.setComments(rset.getString("comments"));
        dining.setUserId(rset.getInt("userId"));
        
    }
    
    public Dining load(int id) throws Exception {
        
        Dining dining = null;
        
        PreparedStatement stmt = null;

        ResultSet rset1 = null;
        ResultSet rset2 = null;
        ResultSet rset3 = null;
        
        try { 

            String sql = "select * from dining where id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            rset1 = stmt.executeQuery();
            if(rset1.next()) { 

                dining = new Dining();
                bind(rset1, dining);
                
                sql = "select * from dining_food where diningId = ?";
                
                stmt = conn.prepareStatement(sql); 
                
                stmt.setInt(1, id);
                
                rset2 = stmt.executeQuery();
                
                while(rset2.next()) {
              
                    sql = "select * from food where id = ?";
                    
                    int foodId = rset2.getInt("foodId");
                    float foodGrams = rset2.getFloat("foodGrams");
                    float currentUnitPrice = rset2.getFloat("currentUnitPrice");
                    
                    Food food = new Food();
                        
                    stmt = conn.prepareStatement(sql); // select * from food where id =  ? 
                    stmt.setInt(1, foodId);
                    rset3 = stmt.executeQuery();
                    
                    if(rset3.next()) {

                        FoodDAO.bind(rset3, food);
                        food.setGrams(foodGrams);
                        if(currentUnitPrice != -1) {
                            food.setUnitPrice(currentUnitPrice);
                        }
                        dining.addFood(food); // add to foodList
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset3 != null) try { rset3.close(); } catch(Exception e) { e.printStackTrace(); }
            if (rset2 != null) try { rset2.close(); } catch(Exception e) { e.printStackTrace(); }
            if (rset1 != null) try { rset1.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return dining;
    }

    public Dining insert(Dining dining) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;

        try {

            stmt = conn.prepareStatement(insertSQL);

            bind(stmt, dining);

            int rowCount = stmt.executeUpdate();

            if (rowCount == 1) { // success 
                
                String sql = "select MAX(id) from dining";
                
                rset = stmt.executeQuery(sql);
                
                if (rset.next()) {
                    
                    int id = rset.getInt(1);
                    dining.setId(id);

                    List<Food> foodList = dining.getFoodList();
                    
                    String insertFoodSQL = "insert into dining_food(diningId, foodId, foodGrams, currentUnitPrice) values (?,?,?,?)";
                    
                    stmt = conn.prepareStatement(insertFoodSQL);

                    for(int i=0; i < foodList.size(); i++) {
                        
                        Food food = foodList.get(i);
                        
                        stmt.setInt(1, dining.getId());
                        stmt.setInt(2, food.getId());
                        stmt.setFloat(3, food.getGrams());
                        stmt.setFloat(4, food.getUnitPrice());
                        
                        rowCount = stmt.executeUpdate();
                        
                        if(rowCount == 1) {
                            //t.out("insert to dining_food was SUCCESS, dining ID == " + dining.getId());
                        }
                        else {
                            //t.out("insert to dining_food FAILED!!!!!!!!!!!!!!!!!!!!!!");
                        }
                    }
                }
            } 
            else {
                //t.out("rowCount == " + rowCount);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return dining;

    }
    
    
    /**
     * Update dining info and delete all foods from dining_food table and add
     * totally new foods.
     */
    public boolean update(Dining dining) throws Exception {

        boolean success = true;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 

            stmt = conn.prepareStatement(updateSQL);

            bind(stmt, dining);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success to dining table

                String delSQL = "delete from dining_food where diningId = ?";
                stmt = conn.prepareStatement(delSQL);
                stmt.setInt(1, dining.getId());
                rowCount = stmt.executeUpdate();
                
                    
                    String insertFoodSQL = "insert into dining_food(diningId, foodId, foodGrams, currentUnitPrice) values (?,?,?,?)";
                    List<Food> foodList = dining.getFoodList();
                    stmt = conn.prepareStatement(insertFoodSQL);

                    for(int i=0; i < foodList.size(); i++) {

                        Food food = foodList.get(i);

                        stmt.setInt(1, dining.getId());
                        stmt.setInt(2, food.getId());
                        stmt.setFloat(3, food.getGrams());
                        stmt.setFloat(4, food.getUnitPrice());

                        rowCount = stmt.executeUpdate();

                        if(rowCount == 1) {
                            //t.out("update eli insert uudet dining_food tauluun onnistui, dining ID == " + dining.getId() + " ja food.name == " + food.getName());
                        }
                        else {
                            //t.out("insert to dining_food FAILED!!!!!!!!!!!!!!!!!!!!!!");
                        }
                    }
                //}
            }
            else {
                success = false;
                //t.out("Dining update rowCount == " + rowCount);
            }
        }
        catch(Exception e) {
            success = false;
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return success;
        
    }
    
    public Dining delete(int id) throws Exception {

        Dining dining = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 
            
            dining = load(id);

            stmt = conn.prepareStatement(deleteSQL);

            stmt.setInt(1, id);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success 
                t.out("Dining delete onnistui, id == " + id);
            }
            else {
                t.out("Dining delete EPÄONNISTUI rowCount == " + rowCount);
                dining = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return dining;
        
    }
    
    public Dining deleteFood(int id, int foodId) throws Exception {

        Dining dining = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql = "delete from dining_food where diningId = ? AND foodId = ?";
        
        
        try { 
            
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.setInt(2, foodId);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success 
                dining = load(id);
            }
            else {
                //t.out("Dining update rowCount == " + rowCount);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return dining;
        
    }
    
    public List<Dining> listAll() throws Exception {
        
        List<Dining> list = new ArrayList<Dining>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(listAllSQL);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                Dining dining = new Dining();
                bind(rset, dining);
                list.add(dining);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return list;
    }
    
    public List<Dining> listByDay(Calendar c, int userId) throws Exception {
        
        List<Dining> list = new ArrayList<Dining>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        ResultSet rset2 = null;
        ResultSet rset3 = null;

        String c1 = MyCalendar.getBeginOfDayString(c);
        String c2 = MyCalendar.getEndOfDayString(c);
        
        String sql = "select * from dining where diningTime BETWEEN '" + c1 + "' AND '" + c2 + "' AND userId = ? order by diningTime";
        
        try { 

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            
            //t.out("SQLLLLLL == " + sql);
            
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                Dining dining = new Dining();
                bind(rset, dining);
                
/********************************/
                
                String loadFoodAndGramsSQL = "select * from dining_food where diningId = ?";
                
                stmt = conn.prepareStatement(loadFoodAndGramsSQL); 
                
                stmt.setInt(1, dining.getId());
                
                rset2 = stmt.executeQuery();
                
                while(rset2.next()) {
                    
                    int foodId = rset2.getInt("foodId");
                    float foodGrams = rset2.getFloat("foodGrams");
                    float up = rset2.getFloat("currentUnitPrice");
                    Food food = new Food();
                    stmt = conn.prepareStatement(FoodDAO.loadSQL); // select * from food where id =  ? 
                    stmt.setInt(1, foodId);
                    
                    rset3 = stmt.executeQuery();
                    
                    if(rset3.next()) {
                        FoodDAO.bind(rset3, food);
                        food.setGrams(foodGrams);
                        if(up != -1) {
                            food.setUnitPrice(up);
                        }
                        
                        dining.addFood(food); // add to foodList
                    }
                }
                
                
/**********************************/                
                
                list.add(dining);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return list;
    }
    
    
    
    
    
    
}
