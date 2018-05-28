package com.codecool.web.servlet.guest;

import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.dao.database.impl.ScheduleDao;
import com.codecool.web.model.Schedule;
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

@WebServlet("/guest/*")
public class GuestServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String uri = req.getRequestURI();
            int idx = uri.lastIndexOf('/');
            int id = Integer.valueOf(uri.substring(idx + 1));

            try(Connection connection = getConnection(req.getServletContext())) {
                ScheduleDatabase db = new ScheduleDao(connection);
                ScheduleService service = new SimpleScheduleService(db);
                Schedule schedule = service.getSchedule(id);
                if (schedule.getPublic()) {
                    sendMessage(resp, 200, schedule);
                }
                else {
                    sendMessage(resp, 401, "Schedule is private!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        catch (NumberFormatException o) {
            sendMessage(resp, 400, o.getMessage());
        }

    }
}
