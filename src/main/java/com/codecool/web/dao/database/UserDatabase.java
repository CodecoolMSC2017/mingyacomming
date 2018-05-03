package com.codecool.web.dao.database;

import com.codecool.web.model.User;

import java.sql.SQLException;

public interface UserDatabase {
    User getUser(int id) throws SQLException;

    User getUser(String name, String password) throws SQLException;

    void addUser(String name, String password, String role) throws SQLException;
}
