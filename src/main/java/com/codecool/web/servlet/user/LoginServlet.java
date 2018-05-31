package com.codecool.web.servlet.user;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.impl.SimpleLoginService;
import com.codecool.web.servlet.AbstractServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public final class LoginServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.trace("Login started");
        if (getUser(req) != null) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "user already logged in");
        } else {
            String jsonString = req.getReader().readLine();

            String userName = getJsonParameter("username", jsonString);
            String password = getJsonParameter("password", jsonString);

            try (Connection connection = getConnection(req.getServletContext())) {
                UserDatabase userDao = new UserDao(connection);
                LoginService loginService = new SimpleLoginService(userDao);

                User user = loginService.loginUser(userName, password);
                logger.debug("user {}", user);
                req.getSession().setAttribute("user", user);
                sendMessage(resp, HttpServletResponse.SC_OK, "succesfull");
                logger.info("Logged in succesfully");

            } catch (SQLException ex) {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                handleSqlError(resp, ex);
                logger.error("Error in logging", ex);
            }
        }
    }
}
