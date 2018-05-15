package com.codecool.web.servlet.task;

import com.codecool.web.dao.database.TaskDatabase;
import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.impl.SimpleTaskService;
import com.codecool.web.servlet.AbstractServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/tasks/*")
public class SingleTaskServlet extends AbstractServlet {


    private static int getId(HttpServletRequest req) {
        String uri = req.getRequestURI();
        int idx = uri.lastIndexOf('/');
        return Integer.valueOf(uri.substring(idx + 1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                TaskDatabase tdb = new TaskDao(connection);
                UserDatabase udb = new UserDao(connection);

                TaskService ts = new SimpleTaskService(tdb, udb);

                sendMessage(resp, 200, ts.getTask(id));

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
            }
        }

        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                TaskDatabase tdb = new TaskDao(connection);
                UserDatabase udb = new UserDao(connection);

                TaskService ts = new SimpleTaskService(tdb, udb);

                ts.removeTask(getUser(req), ts.getTask(id));

                sendMessage(resp, 200, "deleted");

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id or acces denied");
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
        }
    }
}
