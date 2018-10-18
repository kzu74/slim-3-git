package net.slimmer.dao;

import app.commons.util.Trace;
import java.sql.Connection;

/**
 *
 * @author Kaitsu
 */
public class DAO {
    
    //static protected DataSource ds = null;
    
    protected Connection conn = null; // 
    
    public DAO(Connection c) {
        conn = c;
    }
    
    protected Trace t = new Trace(getClass());
    
    public static final String JDBC_CONNECTION = "JDBC_CONNECTION";
    
    
    protected Connection getConnection() throws Exception {
        
        return conn;
        
    }
    
    /**
     * @param number is how many question marks
     * @return string "(?, ?, ?)" when number == 3
     */
    String getSQLQuestionMarks(int number) {
        if(number < 1) return null;
        String qMarks = "(";
        String tmp = "?, ";
        for(int i=1; i <= number; i++) {
            if(i == number) {
                tmp = "?)";
            }
            qMarks += tmp; 
        }
        return qMarks;
    }
    
}
