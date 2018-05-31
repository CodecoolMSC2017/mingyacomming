package com.codecool.web.servlet.day;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.dao.database.impl.DayDao;
import com.codecool.web.model.Day;
import com.codecool.web.service.DayService;
import com.codecool.web.service.impl.SimpleDayService;
import com.codecool.web.servlet.AbstractServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;



@WebServlet("/days")
public class DaysServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(DaysServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Day choose");
        String id = req.getParameter("scheduleId");

        logger.debug("schedule id {}", id);

        try {
            int scheduleId = Integer.parseInt(id);

            try (Connection connection = getConnection(req.getServletContext())) {
                DayDatabase ddb = new DayDao(connection);
                DayService ds = new SimpleDayService(ddb);

                int newid = Integer.parseInt(id);

                sendMessage(resp, 200, ds.getScheduleDays(newid));
                logger.info("Get days succesfully");
            } catch (SQLException e) {
                sendMessage(resp, 500, "sqlserver is down");
                logger.error("Error in connection", e);
            }
        } catch (NumberFormatException e) {
            sendMessage(resp, 400, "error in scheduleID");
            logger.error("Error in get schedulr id", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getReader().readLine();

        String id = req.getParameter("scheduleId");
        String name = getJsonParameter("name", jsonString);

        try {
            int scheduleId = Integer.parseInt(id);


            try(Connection connection = getConnection(req.getServletContext())) {
                DayDatabase ddb = new DayDao(connection);
                DayService ds = new SimpleDayService(ddb);
                if (ds.getDays().size() < 7) {
                    int dayId = ds.addDay(new Day(name, scheduleId));
                    String message = "days/" + dayId;
                    sendMessage(resp, 200, message);
                } else {
                    sendMessage(resp, 500, "maximum 7 days allowed in a schedule");
                }

            } catch (SQLException e) {
                sendMessage(resp, 500, "sqlserver is down");
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, "error converting numbers");
        }
    }
}
