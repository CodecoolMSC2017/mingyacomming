package com.codecool.web.servlet;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.impl.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
@WebServlet("/task")
public class TaskServlet extends  AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = getUser(req).getId();

        try(Connection connection = getConnection(req.getServletContext())) {
            TaskDatabase tdb = new TaskDao(connection);
            UserDatabase udb = new UserDao(connection);

            TaskService ts = new SimpleTaskService(tdb, udb);

            sendMessage(resp, 200, ts.getTasks(userId));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
