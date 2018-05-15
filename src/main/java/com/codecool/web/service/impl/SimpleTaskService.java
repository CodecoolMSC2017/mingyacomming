package com.codecool.web.service.impl;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;

import java.sql.SQLException;
import java.util.List;

public class SimpleTaskService implements TaskService {

    private TaskDatabase tdb;
    private UserDatabase udb;

    public SimpleTaskService(TaskDatabase tdb, UserDatabase udb) {
        this.tdb = tdb;
        this.udb = udb;
    }

    @Override
    public List<Task> getTasks(int userId) throws SQLException {
        return tdb.getTasksByUser(userId);
    }

    @Override
    public int addTask(int userId, String name, String description) throws SQLException {
        Task task = new Task(userId, name, description);
        return tdb.addTask(task);
    }

    public void removeTask(Task task) throws SQLException {
        tdb.removeTask(task);
    }

    public List<Task> getUserTasks(int userId) throws SQLException {
        return tdb.getTasksByUser(userId);
    }

    public Task getTask(int id) throws SQLException{
        return tdb.getTask(id);
    }
}
