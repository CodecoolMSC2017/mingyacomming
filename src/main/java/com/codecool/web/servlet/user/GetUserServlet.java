package com.codecool.web.servlet.user;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/users/*")
public class GetUserServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        int idx = uri.lastIndexOf('/');
        int id = Integer.valueOf(uri.substring(idx + 1));

        try(Connection connection = getConnection(req.getServletContext())) {
            UserDatabase userDatabase = new UserDao(connection);
            User user = userDatabase.getUser(id);
            sendMessage(resp, 200, user);
        } catch (SQLException e) {
            sendMessage(resp, 500, e.getMessage());
        }
    }
}
