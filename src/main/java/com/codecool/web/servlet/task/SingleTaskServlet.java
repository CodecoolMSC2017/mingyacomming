package com.codecool.web.servlet.task;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.impl.SimpleTaskService;
import com.codecool.web.servlet.AbstractServlet;
import com.codecool.web.servlet.slot.SingleSlotServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/tasks/*")
public class SingleTaskServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(SingleTaskServlet.class);

    private static int getId(HttpServletRequest req) {
        String uri = req.getRequestURI();
        int idx = uri.lastIndexOf('/');
        return Integer.valueOf(uri.substring(idx + 1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started getting task");
        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                TaskDatabase tdb = new TaskDao(connection);
                UserDatabase udb = new UserDao(connection);

                TaskService ts = new SimpleTaskService(tdb, udb);
                logger.debug("Task: {}", ts.getTask(id).getName());
                sendMessage(resp, 200, ts.getTask(id));
                logger.info("Task got succesfully");

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
                logger.error("Error in getting task", e);
            }
        }

        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
            logger.error("Error in getting task", e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started deleting task");
        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                TaskDatabase tdb = new TaskDao(connection);
                UserDatabase udb = new UserDao(connection);

                TaskService ts = new SimpleTaskService(tdb, udb);

                ts.removeTask(getUser(req), ts.getTask(id));
                logger.debug("task: {}", ts.getTask(id).getName());
                sendMessage(resp, 200, "deleted");
                logger.info("Task deleted");

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id or acces denied");
                logger.error("Error in deleting task", e);
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
            logger.error("Error in deleting task", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started editing task");
        User user = getUser(req);

        String jsonString = req.getReader().readLine();

        String name = getJsonParameter("name", jsonString);
        String description = getJsonParameter("description", jsonString);
        int id = getId(req);

        try(Connection connection = getConnection(req.getServletContext())) {
            TaskDatabase tdb = new TaskDao(connection);
            UserDatabase udb = new UserDao(connection);

            TaskService ts = new SimpleTaskService(tdb, udb);

            Task task = new Task(id, 0, name, description);

            ts.editTask(task);
            logger.debug("task: {}", ts.getTask(id).getName());
            sendMessage(resp, 200, "Task updated succesfully");
            logger.info("Task edited succesfully");

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
            logger.error("Error in editing task", e);
        }
    }
}
