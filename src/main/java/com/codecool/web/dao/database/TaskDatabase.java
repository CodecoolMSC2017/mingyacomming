package com.codecool.web.dao.database;

import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface TaskDatabase {

    void addTask(Task task) throws SQLException;

    void removeTask(Task task) throws SQLException;

    Task getTask(int id) throws SQLException;

    List<Task> getTasksByUser(User user) throws SQLException;
}
