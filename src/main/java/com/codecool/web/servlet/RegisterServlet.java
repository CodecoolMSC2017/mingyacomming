package com.codecool.web.servlet;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.exceptions.UserAlreadyExistException;
import com.codecool.web.exceptions.UserNameException;
import com.codecool.web.service.RegisterService;
import com.codecool.web.service.impl.SimpleRegisterService;
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
public class RegisterServlet extends AbstractServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jsonString = req.getReader().readLine();

        String userName = getJsonParameter("username", jsonString);
        String password = getJsonParameter("password", jsonString);

        try (Connection connection = getConnection(req.getServletContext())) {
            UserDatabase ud = new UserDao(connection);
            RegisterService rs = new SimpleRegisterService(ud);
            rs.register(userName, password);
            resp.setStatus(204);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(500);
        } catch (UserAlreadyExistException e) {
            resp.setStatus(400);
            resp.addHeader("error", "User already exists!");
        } catch (UserNameException e) {
            resp.setStatus(400);
            resp.addHeader("error", "invalid username");
        }
    }
}
