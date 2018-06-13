package com.codecool.web.service;

import com.codecool.web.model.User;

import java.sql.SQLException;

public interface LoginService {

    User loginUser(String user, String password) throws SQLException;

    User getUserByEmail(String email) throws SQLException;
}
