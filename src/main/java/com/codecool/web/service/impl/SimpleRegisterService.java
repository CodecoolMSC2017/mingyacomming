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
    public void register(String username, String password, String email) throws SQLException, UserAlreadyExistException, UserNameException {
        if (username.equals("") | username.equals(null)) {
            throw new UserNameException();
        }

        try {
            db.getUser(username, password);
            throw new UserAlreadyExistException();
        }
        catch (SQLException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("name or password is wrong")) {
                db.addUser(username, password, "user", email);
            }
            else {
                throw new SQLException(errorMessage);
            }
        }
    }

}
