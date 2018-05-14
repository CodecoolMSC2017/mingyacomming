package com.codecool.web.servlet;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.TaskDao;
import com.codecool.web.dao.database.impl.UserDao;
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

        ObjectMapper om = new ObjectMapper();

        String json = req.getReader().readLine();
        JsonNode jsonNode = om.readTree(json);

        String userName = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();

        try (Connection connection = getConnection(req.getServletContext())) {
            UserDatabase ud = new UserDao(connection);
            RegisterService rs = new SimpleRegisterService(ud);
            rs.register(userName, password);
            resp.setStatus(204);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
