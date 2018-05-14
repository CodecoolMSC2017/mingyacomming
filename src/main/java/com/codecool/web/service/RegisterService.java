package com.codecool.web.service;

import com.codecool.web.exceptions.UserAlreadyExistException;
import com.codecool.web.exceptions.UserNameException;
import com.codecool.web.model.User;

import java.sql.SQLException;

public interface RegisterService {

    void register(String username, String password) throws SQLException, UserAlreadyExistException, UserNameException;
}
