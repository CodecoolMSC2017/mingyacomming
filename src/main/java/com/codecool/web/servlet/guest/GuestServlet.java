package com.codecool.web.servlet.guest;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.dao.database.impl.DayDao;
import com.codecool.web.dao.database.impl.ScheduleDao;
import com.codecool.web.dao.database.impl.SlotDao;
import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.ScheduleDaySlotTask;
import com.codecool.web.model.SlotTask;
import com.codecool.web.service.DayService;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.SlotService;
import com.codecool.web.service.impl.SimpleDayService;
import com.codecool.web.service.impl.SimpleScheduleService;
import com.codecool.web.service.impl.SimpleSlotService;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                DayDatabase ddb = new DayDao(connection);
                SlotDatabase sdb = new SlotDao(connection);
                ScheduleService service = new SimpleScheduleService(db);
                DayService dayService = new SimpleDayService(ddb);
                SlotService slotService = new SimpleSlotService(sdb);
                Schedule schedule = service.getSchedule(id);
                List<Day> days = dayService.getScheduleDays(schedule.getId());
                List<SlotTask> slotTasks = new ArrayList<>();
                for (Day day : days) {
                    for (SlotTask slotTask : slotService.getSlotTask(day.getId())) {
                        slotTasks.add(slotTask);
                    }
                }

                if (schedule.getPublic()) {
                    sendMessage(resp, 200,new ScheduleDaySlotTask(schedule, days, slotTasks));
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
