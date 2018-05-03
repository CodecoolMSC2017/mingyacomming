package com.codecool.web.service;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {
    List<Task> getTasks(int userId) throws SQLException;

    void addTask(int userId, String name, String description) throws SQLException;
}
