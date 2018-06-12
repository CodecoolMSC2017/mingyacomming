package com.codecool.web.dao.database;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDatabase {

    public List<User> getAllUser() throws SQLException;

    User getUser(int id) throws SQLException;

    User getUser(String name, String password) throws SQLException;

    void addUser(String name, String password, String role, String email) throws SQLException;
}
