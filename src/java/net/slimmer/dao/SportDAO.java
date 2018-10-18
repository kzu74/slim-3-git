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
import javax.servlet.http.HttpServletRequest;
import net.slimmer.model.Sport;

/**
 *
 * @author Kaitsu
 */
public class SportDAO extends DAO {

    Trace t = new Trace();
    
    public static final String loadSQL = "select * from sport where id = ?";
    
    private static String insertSQL = 
    "insert into sport (name, minCalories, maxCalories) values (?,?,?)";

    private static String updateSQL = 
    "update sport set name=?, minCalories=?, maxCalories=? where id = ?";
    
    private static String deleteSQL = "delete from sport where id = ?";
    
    private static String listAllSQL = "select * from sport order by name ASC";
    
    public SportDAO(Connection c) {
        super(c);
    }
    
    
    /**
     * Binds insertSQL or updateSQL to user data. If users id > 0, binds one
     * more "?" to user.id.
     */
    public static void bind(PreparedStatement stmt, Sport sport) throws Exception {

        stmt.setString(1, sport.getName());
        stmt.setInt(2, sport.getMinCalories());
        stmt.setInt(3, sport.getMaxCalories());
        
        if (sport.getId() > 0) {
            stmt.setInt(4, sport.getId());
        }

    }
    
    public static void bind(ResultSet rset, Sport sport) throws Exception {

        sport.setId(rset.getInt("id"));
        sport.setName(rset.getString("name"));
        sport.setMinCalories(rset.getInt("minCalories"));
        sport.setMaxCalories(rset.getInt("maxCalories"));
        
    }
    
    public Sport load(int id) throws Exception {
        
        Sport sport = null;
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(loadSQL);
            
            stmt.setInt(1, id);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                sport = new Sport();
                bind(rset, sport);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return sport;
    }

    public Sport insert(Sport sport) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;

        try {

            stmt = conn.prepareStatement(insertSQL);

            bind(stmt, sport);

            int rowCount = stmt.executeUpdate();

            if (rowCount == 1) { // success 
                
                String sql = "select MAX(id) from sport";
                rset = stmt.executeQuery(sql);
                
                if (rset.next()) {
                    
                    int id = rset.getInt(1);
                    sport.setId(id);
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

        return sport;

    }
    
    public boolean update(Sport sport) throws Exception {

        boolean success = true;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        
        try { 

            stmt = conn.prepareStatement(updateSQL);

            bind(stmt, sport);
            
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
    
    public Sport delete(int id) throws Exception {

        Sport sport = null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 
            
            sport = load(id);

            stmt = conn.prepareStatement(deleteSQL);

            stmt.setInt(1, id);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1) { // success 
                sport = null;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return sport;
        
    }
    
    public List<Sport> listByPartOfName(String part) throws Exception {
        
        List<Sport> list = new ArrayList<Sport>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        String sql = "select * from sport where name LIKE'%" + part + "%'";
        
        try { 

            stmt = conn.prepareStatement(sql);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Sport sport = new Sport();
                bind(rset, sport);
                list.add(sport);
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
    
    
    public List<Sport> listAll() throws Exception {
        
        List<Sport> list = new ArrayList<Sport>();
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = conn.prepareStatement(listAllSQL);
                        
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                Sport sport = new Sport();
                bind(rset, sport);
                list.add(sport);
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
