package com.codecool.web.servlet.item;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.dao.database.ItemDatabase;
import com.codecool.web.dao.database.impl.DayDao;
import com.codecool.web.dao.database.impl.ItemDao;
import com.codecool.web.model.Item;
import com.codecool.web.model.User;
import com.codecool.web.service.DayService;
import com.codecool.web.service.ItemService;
import com.codecool.web.service.impl.SimpleDayService;
import com.codecool.web.service.impl.SimpleItemService;
import com.codecool.web.servlet.AbstractServlet;
import com.codecool.web.servlet.day.SingleDayServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/item")
public class ItemServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(SingleDayServlet.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode itemJson = om.readTree(req.getReader().readLine());

        logger.trace("Request(POST /item): Add item");
        try {
            try (Connection connection = getConnection(req.getServletContext())) {
                ItemDatabase idb = new ItemDao(connection);
                ItemService is = new SimpleItemService(idb);

                User user = (User) req.getSession().getAttribute("user");
                Item item = new Item(
                        0,
                        itemJson.get("name").asText(),
                        itemJson.get("quantity").asInt(),
                        itemJson.get("imageUrl").asText()
                );

                is.addItem(user.getId(), item);

                logger.info("Request(POST /item): Item added");
            } catch (SQLException e) {
                e.printStackTrace();
                sendMessage(resp, 400, "bad id");
                logger.error("Request(POST /item): Connection error", e);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sendMessage(resp, 400, e.getMessage());
            logger.error("Request(POST /item): Get id error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.trace("Request(GET /item?itemName={ }): Get item");
        try {
            try (Connection connection = getConnection(req.getServletContext())) {
                ItemDatabase idb = new ItemDao(connection);
                ItemService is = new SimpleItemService(idb);

                User user = (User) req.getSession().getAttribute("user");

                Item item = is.getItemByItemName(user.getId(), req.getParameter("itemName"));
                System.out.println(item.getQuantity());

                sendMessage(resp, 200, item);
                logger.info("Request(GET /item?itemName={ }): Item returned");
            } catch (SQLException e) {
                e.printStackTrace();
                sendMessage(resp, 400, "bad id");
                logger.error("Request(GET /item?itemName={ }): Connection error", e);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sendMessage(resp, 400, e.getMessage());
            logger.error("Request(GET /item?itemName={ }): Get id error", e);
        }
    }
}
