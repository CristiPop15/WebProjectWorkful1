package com.workful.handler;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourcePool {

	private static DataSourcePool dataSourcePool;
    private ComboPooledDataSource cpds;

	private DataSourcePool() throws IOException, SQLException, PropertyVetoException {
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/workful");
        cpds.setUser("root");
        cpds.setPassword("");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(50);
        cpds.setMaxStatements(180);


    }

    public static DataSourcePool getInstance() throws IOException, SQLException, PropertyVetoException {
       
    	if (dataSourcePool == null) {
            dataSourcePool = new DataSourcePool();
            return dataSourcePool;
        } else {
            return dataSourcePool;
        }
        
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }

}
