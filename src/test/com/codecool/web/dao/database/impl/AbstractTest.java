package com.codecool.web.dao.database.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class AbstractTest {

    private String getQueryString() {
        Scanner scanner = new Scanner(AbstractTest.class.getResourceAsStream("/init.sql"));
        String sql = "";
        while (scanner.hasNext()) {
            sql += scanner.nextLine();
        }
        return sql;
    }

    public void runInitStript(Connection connection) throws FileNotFoundException, SQLException {
        try(Statement st = connection.createStatement()) {
            st.execute(getQueryString());
        }
    }

    public Connection getConnection() throws SQLException {
        String USER = System.getProperty("dbUser");
        String PASS = System.getProperty("dbPass");
        String DB_URL = System.getProperty("dbUrl");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
