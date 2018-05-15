package com.codecool.web.servlet.user;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.exceptions.UserAlreadyExistException;
import com.codecool.web.exceptions.UserNameException;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.impl.SimpleRegisterService;
import com.codecool.web.servlet.AbstractServlet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jsonString = req.getReader().readLine();

        String userName = getJsonParameter("username", jsonString);
        String password = getJsonParameter("password", jsonString);

        try (Connection connection = getConnection(req.getServletContext())) {
            UserDatabase ud = new UserDao(connection);
            RegisterService rs = new SimpleRegisterService(ud);
            rs.register(userName, password);
            sendMessage(resp, 200, "user registered");

        } catch (SQLException e) {
            handleSqlError(resp, e);
        } catch (UserAlreadyExistException e) {
            sendMessage(resp, 400, "user already exists");
        } catch (UserNameException e) {
            sendMessage(resp, 400, "user cannot be null");
        }
    }
}
