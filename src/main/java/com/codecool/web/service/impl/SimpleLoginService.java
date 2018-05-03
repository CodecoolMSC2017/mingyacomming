package com.codecool.web.service.impl;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;

import java.sql.SQLException;

public class SimpleLoginService implements LoginService {

    private UserDatabase db;

    public SimpleLoginService(UserDatabase db) {
        this.db = db;
    }

    @Override
    public User loginUser(String user, String password) throws SQLException {
        return db.getUser(user, password);
    }
}
