package com.codecool.web.servlet.schedules;

import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.dao.database.impl.ScheduleDao;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.impl.SimpleScheduleService;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/schedules/*")
public class SingleScheduleServlet extends AbstractServlet{

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
                ScheduleDatabase sd = new ScheduleDao(connection);

                ScheduleService ss = new SimpleScheduleService(sd);

                sendMessage(resp, 200, ss.getSchedule(id));

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
                ScheduleDatabase sd = new ScheduleDao(connection);

                ScheduleService ss = new SimpleScheduleService(sd);

                ss.removeSchedule(id);

                sendMessage(resp, 200, "deleted");

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String jsonString = req.getReader().readLine();

        String name = getJsonParameter("name", jsonString);

        try(Connection connection = getConnection(req.getServletContext())) {
            ScheduleDatabase sdb = new ScheduleDao(connection);

            ScheduleService scheduleService = new SimpleScheduleService(sdb);

            scheduleService.updateSchedule(name, id);

            sendMessage(resp, 200, "Schedule updated succesfully");

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
        }
    }
}
