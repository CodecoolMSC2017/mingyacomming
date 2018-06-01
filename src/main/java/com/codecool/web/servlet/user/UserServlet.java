package com.codecool.web.servlet.user;

import com.codecool.web.model.User;
import com.codecool.web.servlet.AbstractServlet;
import com.codecool.web.servlet.slot.SingleSlotServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started getting user");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        logger.debug("user: {}", user);
        sendMessage(resp, 200, user);
        logger.info("User got succesfully");
    }
}
