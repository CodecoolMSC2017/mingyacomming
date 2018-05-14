package com.codecool.web.service.impl;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.exceptions.UserAlreadyExistException;
import com.codecool.web.exceptions.UserNameException;
import com.codecool.web.model.User;
import com.codecool.web.service.RegisterService;

import java.sql.SQLException;

public class SimpleRegisterService implements RegisterService{

    private UserDatabase db;

    public SimpleRegisterService(UserDatabase db) {
        this.db = db;
    }

    @Override
    public void register(String username, String password) throws SQLException, UserAlreadyExistException, UserNameException {
        if (username.equals("") | username.equals(null)) {
            throw new UserNameException();
        }
        if (db.getUser(username, password) == null) {
            db.addUser(username, password, "user");
            return;
        }
        throw new UserAlreadyExistException();
    }

}
