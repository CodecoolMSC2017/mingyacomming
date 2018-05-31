package com.codecool.web.servlet.slot;

import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.dao.database.impl.SlotDao;
import com.codecool.web.model.Slot;
import com.codecool.web.service.SlotService;
import com.codecool.web.service.impl.SimpleSlotService;
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

@WebServlet("/slots")
public class SlotsServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(SlotsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started getting slots");
        int id = Integer.parseInt(req.getParameter("dayId"));

        try {

            try(Connection connection = getConnection(req.getServletContext())) {
                SlotDatabase sdb = new SlotDao(connection);
                SlotService ss = new SimpleSlotService(sdb);

                sendMessage(resp, 200, ss.getSlotTask(id));
                logger.info("Slots got succesfully");

            } catch (SQLException e) {
                e.printStackTrace();
                sendMessage(resp, 500, "sqlserver is down");
                logger.error("Error in getting slots" ,e);
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            sendMessage(resp, 400, "error in dayID");
            logger.error("Error in getting slots", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started to create new slot");
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
                logger.info("slot created succesfully");

            } catch (SQLException e) {
                sendMessage(resp, 500, "sqlserver is down");
                logger.error("error in creating slot", e);
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, "error converting numbers");
            logger.error("error in creating slot", e);
        }
    }
}
