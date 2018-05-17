package com.codecool.web.servlet.slot;

import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.dao.database.impl.SlotDao;
import com.codecool.web.model.Slot;
import com.codecool.web.service.SlotService;
import com.codecool.web.service.impl.SimpleSlotService;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/slots")
public class SlotsServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("dayId"));

        try {

            try(Connection connection = getConnection(req.getServletContext())) {
                SlotDatabase sdb = new SlotDao(connection);
                SlotService ss = new SimpleSlotService(sdb);

                sendMessage(resp, 200, ss.getSlotTask(id));

            } catch (SQLException e) {
                sendMessage(resp, 500, "sqlserver is down");
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, "error in dayID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonString = req.getReader().readLine();

        String id = req.getParameter("dayId");
        String time = getJsonParameter("time", jsonString);
        String task = getJsonParameter("taskId", jsonString);

        try {
            int dayId = Integer.parseInt(id);
            int slotTime = Integer.parseInt(time);
            int taskId = Integer.parseInt(task);

            try(Connection connection = getConnection(req.getServletContext())) {
                SlotDatabase sdb = new SlotDao(connection);
                SlotService ss = new SimpleSlotService(sdb);
                int slotId = ss.addSlot(new Slot(0, slotTime, taskId, dayId));
                String message = "slots/" + slotId;
                sendMessage(resp, 200, message);

            } catch (SQLException e) {
                sendMessage(resp, 500, "sqlserver is down");
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, "error converting numbers");
        }
    }
}
