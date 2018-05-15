package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends AbstractDao implements TaskDatabase{

    public TaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public void addTask(Task task) throws SQLException {
        String sql = "INSERT into tasks (name, description, user_id) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, task.getName());
            ps.setString(2, task.getDescription());
            ps.setInt(3, task.getUserId());
            ps.executeQuery();
        }
    }

    @Override
    public void removeTask(Task task) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, task.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }

    }

    @Override
    public Task getTask(int id) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Task(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
                }
                return null;
            }
        }
    }

    @Override
    public List<Task> getTasksByUser(User user) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        List<Task> taskList = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(4, user.getId());
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                   Task task = new Task(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
                   taskList.add(task);
                }
            }
        }
        return taskList;
    }
}
