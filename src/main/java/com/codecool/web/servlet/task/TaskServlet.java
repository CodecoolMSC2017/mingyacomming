package com.codecool.web.servlet.task;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.impl.SimpleTaskService;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
@WebServlet("/tasks")
public class TaskServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        try(Connection connection = getConnection(req.getServletContext())) {
            TaskDatabase tdb = new TaskDao(connection);
            UserDatabase udb = new UserDao(connection);

            TaskService ts = new SimpleTaskService(tdb, udb);

            sendMessage(resp, 200, ts.getTasks(user.getId()));

        } catch (SQLException e) {
            sendMessage(resp, 500, "öccázerror");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        String jsonString = req.getReader().readLine();

        String name = getJsonParameter("name", jsonString);
        String description = getJsonParameter("description", jsonString);

        try(Connection connection = getConnection(req.getServletContext())) {
            TaskDatabase tdb = new TaskDao(connection);
            UserDatabase udb = new UserDao(connection);

            TaskService ts = new SimpleTaskService(tdb, udb);

            String message = "users/" + ts.addTask(user.getId(), name, description);

            sendMessage(resp, HttpServletResponse.SC_OK, message);

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
        }

    }
}
