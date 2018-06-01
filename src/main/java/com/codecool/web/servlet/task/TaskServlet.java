package com.codecool.web.servlet.task;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.impl.SimpleTaskService;
import com.codecool.web.servlet.AbstractServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started getting tasks");
        User user;
        String userId = req.getParameter("userId");
        if (userId == null) {
            user = getUser(req);
        } else {
            user = new User(Integer.parseInt(userId), "", "", "");
        }

        try(Connection connection = getConnection(req.getServletContext())) {
            TaskDatabase tdb = new TaskDao(connection);
            UserDatabase udb = new UserDao(connection);

            TaskService ts = new SimpleTaskService(tdb, udb);

            sendMessage(resp, 200, ts.getTasks(user));
            logger.info("Task got succesfully");

        } catch (SQLException e) {
            sendMessage(resp, 500, "öccázerror");
            logger.error("Error in getting tasks", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started creating task");
        User user = getUser(req);

        String jsonString = req.getReader().readLine();

        String name = getJsonParameter("name", jsonString);
        String description = getJsonParameter("description", jsonString);

        try(Connection connection = getConnection(req.getServletContext())) {
            TaskDatabase tdb = new TaskDao(connection);
            UserDatabase udb = new UserDao(connection);

            TaskService ts = new SimpleTaskService(tdb, udb);

            String message = "tasks/" + ts.addTask(user.getId(), name, description);

            sendMessage(resp, HttpServletResponse.SC_OK, message);
            logger.info("Task created succesfully");

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
            logger.error("Error in creating task", e);
        }

    }
}
