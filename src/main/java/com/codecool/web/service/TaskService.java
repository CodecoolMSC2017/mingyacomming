package com.codecool.web.service;

import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {
    List<Task> getTasks(int userId) throws SQLException;

    int addTask(int userId, String name, String description) throws SQLException;

    void removeTask(Task task) throws SQLException;

    List<Task> getUserTasks(int userId) throws SQLException;

    Task getTask(int id) throws SQLException;

    void editTask(Task task) throws SQLException;

}
