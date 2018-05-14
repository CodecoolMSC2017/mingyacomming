package com.codecool.web.service.impl;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.model.User;
import com.codecool.web.service.RegisterService;

import java.sql.SQLException;

public class SimpleRegisterService implements RegisterService{

    private UserDatabase db;

    public SimpleRegisterService(UserDatabase db) {
        this.db = db;
    }

    @Override
    public User register(String username, String password) throws SQLException {
        db.addUser(username, password, "user");
        return db.getUser(username, password);
    }
}
