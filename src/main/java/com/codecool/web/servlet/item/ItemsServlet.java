package com.codecool.web.servlet.item;

import com.codecool.web.dao.database.ItemDatabase;
import com.codecool.web.dao.database.impl.ItemDao;
import com.codecool.web.model.User;
import com.codecool.web.service.ItemService;
import com.codecool.web.service.impl.SimpleItemService;
import com.codecool.web.servlet.AbstractServlet;
import com.codecool.web.servlet.day.SingleDayServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/items")
public class ItemsServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(SingleDayServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Request(GET /items): Get items");
        try {
            try (Connection connection = getConnection(req.getServletContext())) {
                ItemDatabase idb = new ItemDao(connection);
                ItemService is = new SimpleItemService(idb);

                User user = (User) req.getSession().getAttribute("user");

                sendMessage(resp, 200, is.getItemsByUserId(user.getId()));
                logger.info("Request(GET /items): Returned items");

            } catch (SQLException e) {
                e.printStackTrace();
                sendMessage(resp, 400, "bad id");
                logger.error("Request(GET /items): Connection error", e);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            sendMessage(resp, 400, e.getMessage());
            logger.error("Request(GET /items): Get id error", e);
        }
    }
}
