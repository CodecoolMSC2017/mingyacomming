package com.codecool.web.servlet.day;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.dao.database.SlotDatabase;
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

@WebServlet("/days/*")
public class SingleDayServlet extends AbstractServlet{

    private static final Logger logger = LoggerFactory.getLogger(SingleDayServlet.class);

    private static int getId(HttpServletRequest req) {
        String uri = req.getRequestURI();
        int idx = uri.lastIndexOf('/');
        return Integer.valueOf(uri.substring(idx + 1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.trace("Get Single day started");
        try {
            int id = getId(req);
            logger.debug("day  {}", id);
            try(Connection connection = getConnection(req.getServletContext())) {
                DayDatabase ddb = new DayDao(connection);

                DayService ds = new SimpleDayService(ddb);

                sendMessage(resp, 200, ds.getScheduleDays(id));
                logger.info("Single day connection succesfully");
            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
                logger.error("Single day connection error", e);
            }
            } catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
            logger.error("Single day get id error", e);
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

        String jsonString = req.getReader().readLine();
        String name = getJsonParameter("name", jsonString);
        int id = getId(req);


        try(Connection connection = getConnection(req.getServletContext())) {
            DayDatabase ddb = new DayDao(connection);

            DayService dayService = new SimpleDayService(ddb);
            Day day = dayService.getDay(id);
            day.setName(name);
            dayService.updateDay(day);
            sendMessage(resp, 200, "succesfully edited");

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
        }
    }
}
