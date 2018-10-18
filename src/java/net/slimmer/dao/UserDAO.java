/*
 * UserDAO.java
 *
 * Created on 27. hein�kuuta 2011, 18:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.slimmer.dao;


import net.slimmer.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author Kaitsu
 */
public class UserDAO extends DAO {    
    
    // kaitsu.email@gmail.com, kaitsu
    // lissu737@gmail.com, lissu5221
    
    public UserDAO(Connection c) {
        super(c);
    }
    
    private String insertSQL = 
        "insert into user (firstName, lastName, weight, height, gender, email, phone, password, birthDay, street, zip, city, userGroup)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?, SHA1(?), ?, ?, ?, ?, ?)";
    
    private String updateSQL = 
        "update user set firstName = ?, lastName = ?, weight = ?, height = ?,  gender = ?, email = ?, phone = ?, password = ?,"
        + " birthDay = ?, street = ?, zip = ?, city = ?, userGroup = ? WHERE id = ?";
    
    public void bind(User user, ResultSet rset) throws Exception {

        user.setId(rset.getInt("id"));
        user.setFirstName(rset.getString("firstname"));
        user.setLastName(rset.getString("lastname"));

        user.setWeight(rset.getInt("weight"));
        user.setHeight(rset.getInt("height"));
        
        user.setGender(rset.getString("gender"));
        user.setEmail(rset.getString("email"));
        user.setPhone(rset.getString("phone"));
        user.setPassword(rset.getString("password"));
        user.setBirthDay(rset.getDate("birthDay"));
        user.setStreet(rset.getString("street"));
        user.setZip(rset.getString("zip"));
        user.setCity(rset.getString("city"));
        user.setUserGroup(rset.getString("usergroup"));
        
    }
    
    /**
     * Binds insertSQL or updateSQL to user data. If users id > 0, 
     * binds one more "?" to user.id.
     */
    public void bind(PreparedStatement stmt, User u) throws Exception {
        
        stmt.setString(1, u.getFirstName());
        stmt.setString(2, u.getLastName());

        stmt.setInt(3, u.getWeight());
        stmt.setInt(4, u.getHeight());
        
        stmt.setString(5, u.getGender());
        stmt.setString(6, u.getEmail());
        stmt.setString(7, u.getPhone());
        stmt.setString(8, u.getPassword());
        stmt.setDate(9, u.getBirthDay());
        stmt.setString(10, u.getStreet());
        stmt.setString(11, u.getZip());
        stmt.setString(12, u.getCity());
        stmt.setString(13, u.getUserGroup());
        if(u.getId() > 0) {
            stmt.setInt(14, u.getId());
        }
        
    }
    

    public User load(int id) throws Exception {
        
        User u = null;
        String sql = "select * from user where id = ?";
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                u = new User();
                bind(u, rset);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return u;
    }
    
    public User load(String email) throws Exception {
        
        User u = null;
        if(email == null) return null;
        String sql = "select * from user where email = ?";
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, email);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                u = new User();
                bind(u, rset);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return u;
    }

    /**
     * Load first user by firstname and lastname.
     */
    public User load(String fn, String ln) throws Exception {
        
        User u = null;
        String sql = "select * from user where firstName = ? and lastName = ?";
        ResultSet rset = null;
        PreparedStatement stmt = null;
        
        try { 

            stmt = getConnection().prepareStatement(sql);
            
            stmt.setString(1, fn);
            stmt.setString(2, ln);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                u = new User();
                bind(u, rset);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return u;
    }

    public User login(String email, String pass) {
        
        User user = null;
        String sql = "select * from user where email = ? and password = SHA1(?)";
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, pass);
            
            rset = stmt.executeQuery();
            
            if(rset.next()) { // there was reserved time between c1 and c2
            
                user = new User();
                bind(user, rset);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }
        return user;
        
    }

    public List<User> getEmployees() throws Exception {
        
        List<User> list = new ArrayList<User>();
        String sql = "select * from user where usergroup = ?";
        t.out("SQL == " + sql);
        
        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 

            stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, "employee");
            
            rset = stmt.executeQuery();
            
            while(rset.next()) { // there was reserved time between c1 and c2
            
                User user = new User();
                bind(user, rset);
                list.add(user);
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
    
    
    public User update(User u) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 

            stmt = getConnection().prepareStatement(updateSQL);

            bind(stmt, u);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success 
                t.out("User updatettiin, rowCount == 1..........");
            }
            else {
                t.out("User update rowCount == " + rowCount);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return u;
        
    }
    
    public User insert(User u) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rset = null;
        
        try { 

            stmt = getConnection().prepareStatement(insertSQL);

            bind(stmt, u);
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount == 1) { // success 
                t.out("CalendarEvent tallennettiin..........");
                String sql = "select MAX(id) from user";
                rset = stmt.executeQuery(sql);
                if(rset.next()) {
                    int id = rset.getInt(1);
                    u.setId(id);
                }
            }
            else {
                t.out("rowCount == " + rowCount);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rset != null) try { rset.close(); } catch(Exception e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch(Exception e) { e.printStackTrace(); }
        }

        return u;
        
    }
    
}
