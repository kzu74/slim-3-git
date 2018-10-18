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

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.Statistics;

public class DataSourceBone {

    private static DataSourceBone datasource;
    private static Trace t = new Trace(DataSourceBone.class);
    private BoneCP boneCP = null;
    BoneCPConfig config = new BoneCPConfig();	// create a new configuration object

    private DataSourceBone() {

        try {

            Class.forName("com.mysql.jdbc.Driver"); 	// load the DB driver
            
            config.setJdbcUrl("jdbc:mysql://localhost/kaitsu74_slim?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10");	// set the JDBC url
//            config.setJdbcUrl("jdbc:mysql://mysql3000.mochahost.com:3306/kaitsu74_slim?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=10");	// set the JDBC url

            config.setUsername("root");			// set the username
//            config.setUsername("kaitsu74_root");			// set the username

            config.setPassword("smurffi");				// set the password

            config.setAcquireIncrement(5);				// (other config options here)
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(20);

            boneCP = new BoneCP(config); 	// setup the connection pool
            t.out("BONE CP IN DATASOURCE == " + boneCP);


        } catch (Exception e) {
            e.printStackTrace();
        }
        /*    
         connection.close();				// close the connection
         connectionPool.shutdown();			// close the connection pool        
         */

    }

    public static DataSourceBone getInstance() throws IOException, SQLException {
        
        if (datasource == null) {
        
            datasource = new DataSourceBone();
            
            return datasource;
        } 
        else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {


        Statistics st = boneCP.getStatistics();
        t.out("BEFORE getConnection()..............................................");
        t.out("getTotalCreatedConnections() == " + st.getTotalCreatedConnections());
        t.out("getTotalFree() == " + st.getTotalFree());
        t.out("getTotalLeased() == " + st.getTotalLeased());
        t.out("");

        Connection conn = boneCP.getConnection();

        t.out("AFTER getConnection()...............................................");
        t.out("getTotalCreatedConnections() == " + st.getTotalCreatedConnections());
        t.out("getTotalFree() == " + st.getTotalFree());
        t.out("getTotalLeased() == " + st.getTotalLeased());

        t.out("");

        return conn;
    }
}