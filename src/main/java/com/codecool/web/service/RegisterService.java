package com.codecool.web.service;

import com.codecool.web.model.User;

import java.sql.SQLException;

public interface RegisterService {

    User register(String username, String password) throws SQLException;
}
