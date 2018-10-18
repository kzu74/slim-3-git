/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.slimmer.dao;

/**
 *
 * @author Kaitsu
 */
import app.commons.util.Trace;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;


public class MyDataSource {

    private static DataSource datasource;
    private static MyDataSource mds;
    Trace t = new Trace(getClass());
    
    private MyDataSource() {

        t.out("CREATING MyDataSource, setting all PoolProperties for Tomcat Pool datasource.....");
        
        try {

            PoolProperties p = new PoolProperties();
            p.setUrl("jdbc:mysql://localhost/kaitsu74_slim?useUnicode=true&characterEncoding=utf8");
            p.setDriverClassName("com.mysql.jdbc.Driver");
            p.setUsername("root");
            p.setPassword("smurffi");
            p.setJmxEnabled(false);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
              "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            datasource = new DataSource();
            datasource.setPoolProperties(p); 


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static DataSource getInstance() throws IOException, SQLException {
        
        if (datasource == null) {
        
            mds = new MyDataSource();
            
            return datasource;
        } 
        else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException, IOException {

        return getInstance().getConnection();
    }
}