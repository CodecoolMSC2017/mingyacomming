package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao implements UserDatabase {

    public UserDao(Connection connection) {
        super(connection);
    }

    public List<User> getAllUser() throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(fetchUser(resultSet));
            }
            return users;
        }
    }

    @Override
    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
                return null;
            }
        }
    }

    @Override
    public User getUser(String name, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
                throw new SQLException("name or password is wrong");
            }
        }
    }

    @Override
    public void addUser(String name, String password, String role, String email) throws SQLException {
        String sql = "INSERT into users (name, password, role, email) VALUES(?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.setString(4, email);

            ps.executeUpdate();
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String permission = resultSet.getString("role");
        String email = resultSet.getString("email");
        return new User(id, name, password, permission, email);
    }
}
