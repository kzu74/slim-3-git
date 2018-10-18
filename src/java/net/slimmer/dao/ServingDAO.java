/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.dao;

import app.commons.util.Trace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.slimmer.model.Serving;
import net.slimmer.model.Food;

/**
 *
 * @author Kaitsu
 */
public class ServingDAO extends DAO {

    Trace t = new Trace();
    
    private static String loadSQL = "select * from serving where id = ?";
    
    private static String insertSQL = "insert into serving (name, energy, created, comments, userId) values (?,?,?,?,?)";

    private static String updateSQL = "update serving set name = ?, energy = ?, created = ?, comments = ?, userId = ? where id = ?";
    
    private static String deleteSQL = "delete from serving where id = ?";
    
    private static String listAllSQL = "select * from serving where active = 1 order by created DESC";
    
    //private static String listByDaysOldSQL = "select * from serving where active = 1 order by created";
    
    
    public ServingDAO(Connection c) {
        super(c);
    }

    /**
     * Binds insertSQL or updateSQL to user data. If users id > 0, binds one
     * more "?" to user.id.
     */
    public void bind(PreparedStatement stmt, Serving serving) throws Exception {

        stmt.setString(1, serving.getName());
        stmt.setInt(2, serving.getEnergy());
        stmt.setTimestamp(3, serving.getCreated());
        stmt.setString(4, serving.getComments());
        stmt.setInt(5, serving.getUserId());
        
        if (serving.getId() > 0) {
            stmt.setInt(6, serving.getId());
        }

    }
    
    public void bind(ResultSet rset, Serving serving) throws Exception {

        serving.setId(rset.getInt("id"));
        serving.setName(rset.getString("name"));
        serving.setEnergy(rset.getInt("energy"));
        serving.setCreated(rset.getTimestamp("created"));
        serving.setComments(rset.getString("comments"));
        serving.setUserId(rset.getInt("userId"));
        
    }
    
    public Serving load(int id) throws Exception {
        
        Serving serving = null;
        
        ResultSet rset = null;
        PreparedStatement stmt = null;

        ResultSet rset2 = null;
        ResultSet rset3 = null;

        try { 

            stmt = conn.prepareStatement(loadSQL);
            
            stmt.setInt(1, id);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                serving = new Serving();
                bind(rset, serving);
                
                String loadFoodAndGramsSQL = "select * from serving_food where servingId = ?";
                
                stmt = conn.prepareStatement(loadFoodAndGramsSQL); 
                
                stmt.setInt(1, id);
                
                rset2 = stmt.executeQuery();
                
                
                while(rset2.next()) {
                    
                    int foodId = rset2.getInt("foodId");
                    float foodGrams = rset2.getFloat("foodGrams");
                    
                    Food food = new Food();
                    
                    stmt = conn.prepareStatement(FoodDAO.loadSQL); // select * from food where id =  ? 
                    
                    stmt.setInt(1, foodId);
                    
                    rset3 = stmt.executeQuery();
                    
                    if(rset3.next()) {
                        
                        FoodDAO.bind(rset3, food);
                        
                        food.setGrams(foodGrams);
                        
                        serving.addFood(food); // add to foodList
                        
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset3 != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (rset2 != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return serving;
    }

    public Serving insert(Serving serving) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;

        t.out("userId ============================ " + serving.getUserId());
        try {

            
            stmt = conn.prepareStatement(insertSQL);

            bind(stmt, serving);

            int rowCount = stmt.executeUpdate();

            if (rowCount == 1) { // success 
                
                String sql = "select MAX(id) from serving";
                rset = stmt.executeQuery(sql);
                
                if (rset.next()) {
                    
                    int id = rset.getInt(1);
                    serving.setId(id);

                    List<Food> foodList = serving.getFoodList();
                    
                    String insertFoodSQL = "insert into serving_food(servingId, foodId, foodGrams) values (?,?,?)";
                    
                    stmt = conn.prepareStatement(insertFoodSQL);

                    for(int i=0; i < foodList.size(); i++) {
                        
                        Food food = foodList.get(i);
                        float foodGrams = food.getGrams();
                        
                        stmt.setInt(1, serving.getId());
                        stmt.setInt(2, food.getId());
                        stmt.setFloat(3, foodGrams);
                        
                        rowCount = stmt.executeUpdate();
                        
                        if(rowCount != 1) {
                            // TODO
                        }
                    }
                }
            } 
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return serving;

    }
    
    public boolean update(Serving serving) throws Exception {

        boolean success = true;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 

            stmt = conn.prepareStatement(updateSQL);

            bind(stmt, serving);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success to serving table

                String delSQL = "delete from serving_food where servingId = ?";
                stmt = conn.prepareStatement(delSQL);
                stmt.setInt(1, serving.getId());
                rowCount = stmt.executeUpdate();
                
                if(rowCount > 0) {
                    
                    String insertFoodSQL = "insert into serving_food(servingId, foodId, foodGrams) values (?,?,?)";
                    List<Food> foodList = serving.getFoodList();
                    stmt = conn.prepareStatement(insertFoodSQL);

                    for(int i=0; i < foodList.size(); i++) {

                        Food food = foodList.get(i);
                        float foodGrams = food.getGrams();

                        stmt.setInt(1, serving.getId());
                        stmt.setInt(2, food.getId());
                        stmt.setFloat(3, foodGrams);

                        rowCount = stmt.executeUpdate();

                        if(rowCount != 1) {
                            // TODO
                        }
                    }
                }
            }
            else {
                success = false;
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

    public Serving delete(int id) throws Exception {

        Serving serving = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 
            
            serving = load(id);

            stmt = conn.prepareStatement(deleteSQL);

            stmt.setInt(1, id);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1) { // success 
                serving = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return serving;
        
    }
    
    public Serving deleteFood(int id, int foodId) throws Exception {

        Serving s = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql = "delete from serving_food where servingId = ? AND foodId = ?";
        
        
        try { 
            
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.setInt(2, foodId);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success 
                s = load(id);
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

        return s;
        
    }
    
    
    public List<Serving> listAll() throws Exception {
        
        List<Serving> list = new ArrayList<Serving>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(listAllSQL);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                Serving serving = new Serving();
                bind(rset, serving);
                list.add(serving);
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
    
    /**
     * int days = how many days back to list servings, refers to column 'created'.
     */
    public List<Serving> listByDaysOld(int days) throws Exception {
        
        List<Serving> list = new ArrayList<Serving>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        String sql = "select * from serving where created >= DATE_SUB(CURDATE(), INTERVAL " + days + " DAY)";
        
        try { 

            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();
            
            while(rset.next()) { 
            
                Serving serving = new Serving();
                bind(rset, serving);
                list.add(serving);
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

    
    /**
     * Get serving by name which includes a substring 'part' anywhere in the name. If
     * weighed is true fetch already weighed servings, otherwise other servings.
     */
    public List<Serving> listByPartOfName(String part) throws Exception {
        
        List<Serving> list = new ArrayList<Serving>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        String sql = "select * from serving where name LIKE'%" + part + "%' AND active = 1";
        
        try { 
                
            
            stmt = conn.prepareStatement(sql);

            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Serving serving = new Serving();
                bind(rset, serving);
                list.add(serving);
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
