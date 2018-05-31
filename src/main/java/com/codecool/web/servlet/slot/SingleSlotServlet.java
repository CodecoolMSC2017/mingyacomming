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


@WebServlet("/slots/*")
public class SingleSlotServlet extends AbstractServlet{

    private static final Logger logger = LoggerFactory.getLogger(SingleSlotServlet.class);

    private static int getId(HttpServletRequest req) {
        String uri = req.getRequestURI();
        int idx = uri.lastIndexOf('/');
        return Integer.valueOf(uri.substring(idx + 1));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started to get slot");
        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                SlotDatabase sdb = new SlotDao(connection);

                SlotService ts = new SimpleSlotService(sdb);

                sendMessage(resp, 200, ts.getDaySlots(id));
                logger.info("Slot got succesfully");

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
                logger.error("Error in getting slot", e);
            }
        }

        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
            logger.error("Error in getting slot", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started to delete slot");
        try {
            int id = getId(req);

            try(Connection connection = getConnection(req.getServletContext())) {
                SlotDatabase sdb = new SlotDao(connection);

                SlotService slotService = new SimpleSlotService(sdb);

                slotService.removeSlot(slotService.getSlot(id));

                sendMessage(resp, 200, "deleted");
                logger.info("Slot deleted succesfully");

            } catch (SQLException e) {
                sendMessage(resp, 400, "bad id");
                logger.error("Error in deleting slot", e);
            }
        }
        catch (NumberFormatException e) {
            sendMessage(resp, 400, e.getMessage());
            logger.error("Error in deleting slot", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started editing slot");
        String jsonString = req.getReader().readLine();
        int id = getId(req);

        int time = Integer.parseInt(getJsonParameter("time", jsonString));
        int task_id = Integer.parseInt(getJsonParameter("taskId", jsonString));
        int day_id = Integer.parseInt(getJsonParameter("dayId", jsonString));
        boolean isChecked = Boolean.parseBoolean(getJsonParameter("isChecked", jsonString));
        try(Connection connection = getConnection(req.getServletContext())) {
            SlotDatabase sdb = new SlotDao(connection);

            SlotService slotService = new SimpleSlotService(sdb);
            Slot slot = slotService.getSlot(id);
            slot.setChecked(isChecked);
            slot.setTime(time);
            slot.setDay_id(day_id);
            slot.setTask_id(task_id);

            slotService.updateSlot(slot);

            sendMessage(resp, 200, "Slot updated succesfully");
            logger.info("Slot edited succesfully");

        } catch (SQLException e) {
            e.printStackTrace();
            sendMessage(resp, 400, "something went wrong");
            logger.error("Error in editing slot", e);
        }
    }
}
