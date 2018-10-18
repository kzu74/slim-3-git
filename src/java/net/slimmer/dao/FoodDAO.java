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
import net.slimmer.model.Food;
import net.slimmer.model.User;

/**
 *
 * @author Kaitsu
 */
public class FoodDAO extends DAO {

    Trace t = new Trace(FoodDAO.class);
    
    public static final String loadSQL = "select * from food where id = ?";
    
    private static String insertSQL = 
    "insert into food (name, energy, protein, carbs, fat, fiber, natrium, unitPrice, type, isPublic, userId) values (?,?,?,?,?,?,?,?,?,?,?)";

    private static String updateSQL = 
    "update food set name=?, energy=?, protein=?, carbs=?, fat=?, fiber=?, natrium=?, unitPrice=?, type=?, isPublic=?, userId=? where id = ?";
    
    private static String deleteSQL = "delete from food where id = ?";
    
    private static String listAllSQL = "select * from food order by name ASC";
    
    public FoodDAO(Connection c) {
        super(c);
    }
    
    
    /**
     * Binds insertSQL or updateSQL to user data. If users id > 0, binds one
     * more "?" to user.id.
     */
    public static void bind(PreparedStatement stmt, Food food) throws Exception {

        stmt.setString(1, food.getName());
        stmt.setInt(2, food.getEnergy());
        stmt.setFloat(3, food.getProtein());
        stmt.setFloat(4, food.getCarbs());
        stmt.setFloat(5, food.getFat());
        stmt.setFloat(6, food.getFiber());
        stmt.setFloat(7, food.getNatrium());
        stmt.setFloat(8, food.getUnitPrice());
        stmt.setString(9, food.getType());
        stmt.setBoolean(10, food.getIsPublic());
        stmt.setInt(11, food.getUserId());
        
        
        if (food.getId() > 0) {
            stmt.setInt(12, food.getId());
        }

    }
    
    public static void bind(ResultSet rset, Food food) throws Exception {

        food.setId(rset.getInt("id"));
        food.setName(rset.getString("name"));
        food.setEnergy(rset.getInt("energy"));
        food.setProtein(rset.getFloat("protein"));
        food.setCarbs(rset.getFloat("carbs"));
        food.setFat(rset.getFloat("fat"));
        food.setFiber(rset.getFloat("fiber"));
        food.setNatrium(rset.getFloat("natrium"));
        food.setUnitPrice(rset.getFloat("unitPrice")); // default unit price. Can be altered when saving dining.
        // altered unit price is saved in dining_food table and serving_food table in db, representing current price
        food.setType(rset.getString("type"));
        food.setIsPublic(rset.getBoolean("isPublic"));
        food.setUserId(rset.getInt("userId"));
        
    }
    
    public Food load(int id) throws Exception {
        
        Food food = null;
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(loadSQL);
            
            stmt.setInt(1, id);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                food = new Food();
                bind(rset, food);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return food;
    }

    public Food insert(Food food) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;

        try {

            stmt = conn.prepareStatement(insertSQL);

            bind(stmt, food);

            int rowCount = stmt.executeUpdate();

            if (rowCount == 1) { // success 
                
                String sql = "select MAX(id) from food";
                rset = stmt.executeQuery(sql);
                
                if (rset.next()) {
                    
                    int id = rset.getInt(1);
                    food.setId(id);
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

        return food;

    }
    
    public boolean update(Food food) throws Exception {

        boolean success = true;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        
        try { 

            stmt = conn.prepareStatement(updateSQL);

            bind(stmt, food);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1) { // success 
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
    
    public Food delete(int id) throws Exception {

        Food food = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 
            
            food = load(id);

            stmt = conn.prepareStatement(deleteSQL);

            stmt.setInt(1, id);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1) { // success 
                food = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return food;
        
    }
    
    public List<Food> listByPartOfName(String part) throws Exception {
        
        List<Food> list = new ArrayList<Food>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        String sql = "select * from food where name LIKE'%" + part + "%'";
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Food food = new Food();
                bind(rset, food);
                list.add(food);
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
    
    
    public List<Food> listAll() throws Exception {
        
        List<Food> list = new ArrayList<Food>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(listAllSQL);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Food food = new Food();
                bind(rset, food);
                list.add(food);
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
    
    public List<Food> listByOwner(User u, String ownOrOthers) throws Exception {
        
        List<Food> list = new ArrayList<Food>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        if(u == null) return null;

        String whereClause = "userId = " + u.getId();
        
        if(ownOrOthers.equals("others")) {
            whereClause = "userId != " + u.getId();
        }
        
        String sql = "select * from food where " + whereClause + " order by name ASC";
        
        t.out("sql == " + sql);
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Food food = new Food();
                bind(rset, food);
                list.add(food);
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
