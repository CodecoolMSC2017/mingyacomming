package com.codecool.web.servlet.day;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.dao.database.impl.DayDao;
import com.codecool.web.service.DayService;
import com.codecool.web.service.impl.SimpleDayService;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/days/*")
public class SingleDayServlet extends AbstractServlet{

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
                DayDatabase ddb = new DayDao(connection);

                DayService ds = new SimpleDayService(ddb);

                sendMessage(resp, 200, ds.getScheduleDays(id));

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
            }
            } catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                DayDatabase sdb = new DayDao(connection);

                DayService dayService = new SimpleDayService(sdb);

                dayService.removeDay(dayService.getDay(id));

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

        String name = req.getParameter("name");
        String id = req.getParameter("id");
        int parseID = Integer.parseInt(id);
        int parseName = Integer.parseInt(name);

        try(Connection connection = getConnection(req.getServletContext())) {
            DayDatabase ddb = new DayDao(connection);

            DayService slotService = new SimpleDayService(ddb);

            sendMessage(resp, 200, "succesfully edited");

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
        }
    }
}
