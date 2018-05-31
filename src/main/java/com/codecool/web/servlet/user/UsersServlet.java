package com.codecool.web.servlet.user;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
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
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started getting users");
        try(Connection connection = getConnection(req.getServletContext())) {
            UserDatabase userDatabase = new UserDao(connection);
            List<User> users = userDatabase.getAllUser();
            sendMessage(resp, 200, users);
            logger.info("Users got succesfully");
        } catch (SQLException e) {
            sendMessage(resp, 500, e.getMessage());
            logger.error("Error in getting users", e);
        }
    }
}
