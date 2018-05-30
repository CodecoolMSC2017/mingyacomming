package com.codecool.web.servlet.schedules;

import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.dao.database.impl.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
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

@WebServlet("/schedules")
public class SchedulesServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        String userId = req.getParameter("userId");
        if (userId == null) {
            user = getUser(req);
        } else {
            user = new User(Integer.parseInt(userId), "", "", "");
        }

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDatabase sdb = new ScheduleDao(connection);
            ScheduleService ss = new SimpleScheduleService(sdb);

            sendMessage(resp, 200, ss.getUserSchedules(user));

        } catch (SQLException e) {
            sendMessage(resp, 500, "error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);

        String jsonString = req.getReader().readLine();

        String name = getJsonParameter("name", jsonString);

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDatabase sdb = new ScheduleDao(connection);

            ScheduleService ss = new SimpleScheduleService(sdb);

            String message = "schedules/" + ss.addSchedule(new Schedule(name, user.getId(), false));

            sendMessage(resp, HttpServletResponse.SC_OK, message);

        } catch (SQLException e) {
            sendMessage(resp, 400, "something went wrong");
        }

    }
}
