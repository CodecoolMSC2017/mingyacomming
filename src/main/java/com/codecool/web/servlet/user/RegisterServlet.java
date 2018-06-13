package com.codecool.web.servlet.user;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.exceptions.UserAlreadyExistException;
import com.codecool.web.exceptions.UserNameException;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.impl.SimpleRegisterService;
import com.codecool.web.servlet.AbstractServlet;
import com.codecool.web.servlet.slot.SingleSlotServlet;
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

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.trace("Started register");
        String jsonString = req.getReader().readLine();

        String userName = getJsonParameter("username", jsonString);
        String password = getJsonParameter("password", jsonString);
        String email = getJsonParameter("email", jsonString);

        try (Connection connection = getConnection(req.getServletContext())) {
            UserDatabase ud = new UserDao(connection);
            RegisterService rs = new SimpleRegisterService(ud);
            rs.register(userName, password, email);
            sendMessage(resp, 200, "user registered");
            logger.info("Registered succesfully");

        } catch (SQLException e) {
            handleSqlError(resp, e);
            logger.error("Error in register", e);
        } catch (UserAlreadyExistException e) {
            sendMessage(resp, 400, "user already exists");
            logger.error("Error in register", e);
        } catch (UserNameException e) {
            sendMessage(resp, 400, "user cannot be null");
            logger.error("Error in register", e);
        }
    }
}
