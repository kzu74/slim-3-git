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


import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DataSourceC3p0 {

    private ComboPooledDataSource cpds;
    
    private static DataSourceC3p0 datasource;
    private static Trace t = new Trace(DataSourceC3p0.class);

    private DataSourceC3p0() {
        // load datasource properties
        t.out("Creating DataSource connection pool...");
        cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:mysql://localhost/kcal?useUnicode=true&characterEncoding=utf8");
        cpds.setUser("root");
        cpds.setPassword("smurffi");

        cpds.setInitialPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(30);
        cpds.setMinPoolSize(5);
        cpds.setMaxIdleTime(5);
        //cpds.setMaxStatements(new Integer((String) props.getProperty("maxStatements")));
    }

    public static DataSourceC3p0 getInstance() throws IOException, SQLException {
        if (datasource == null) {
              datasource = new DataSourceC3p0();
              return datasource;
            } else {
              return datasource;
            }
    }

    public Connection getConnection() throws SQLException {
        t.out("cpds.getNumUnclosedOrphanedConnections() == " + cpds.getNumUnclosedOrphanedConnections());
        t.out("cpds.getMaxIdleTime() == " + cpds.getMaxIdleTime());
        t.out("cpds.getNumIdleConnections() == " + cpds.getNumIdleConnections());
        t.out("cpds.getInitialPoolSize() == " + cpds.getInitialPoolSize());
        t.out("cpds.getMaxConnectionAge() == " + cpds.getMaxConnectionAge());
        t.out("cpds.getNumBusyConnections() == " + cpds.getNumBusyConnections());
        t.out("cpds.getNumUnclosedOrphanedConnections() == " + cpds.getNumUnclosedOrphanedConnections());
        
        //cpds.softResetAllUsers();
        
        return cpds.getConnection();
    }
}