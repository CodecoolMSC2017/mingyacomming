package com.codecool.web.servlet.user;

import com.codecool.web.servlet.AbstractServlet;
import com.codecool.web.servlet.slot.SingleSlotServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Logout started");
        req.getSession().invalidate();
        sendMessage(resp, 204, "logout successful");
        logger.info("Logged out");
    }
}
