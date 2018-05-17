package com.codecool.web.dao.database.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class AbstractTest {

    private String getQueryString() {
        Scanner scanner = new Scanner(AbstractTest.class.getResourceAsStream("/testInit.sql"));
        String sql = "";
        while (scanner.hasNext()) {
            sql += scanner.nextLine();
        }
        return sql;
    }

    public void runInitStript(Connection connection) throws SQLException {
        try(Statement st = connection.createStatement()) {
            st.execute(getQueryString());
        }
    }

    public Connection getConnection() throws SQLException {
        String USER = System.getProperty("dbUser", "postgres");
        String PASS = System.getProperty("dbPass", "admin");
        String DB_URL = System.getProperty("dbUrl", "jdbc:postgresql://localhost:5432/mingyagyuvok");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
