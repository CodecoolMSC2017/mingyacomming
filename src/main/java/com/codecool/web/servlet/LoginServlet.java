package com.codecool.web.servlet;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.impl.SimpleLoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public final class LoginServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = req.getReader().readLine();

        String userName = getJsonParameter("username", jsonString);
        String password = getJsonParameter("password", jsonString);

        try (Connection connection = getConnection(req.getServletContext())) {
            UserDatabase userDao = new UserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);

            User user = loginService.loginUser(userName, password);
            req.getSession().setAttribute("user", user);
            resp.setStatus(204);
            //sendMessage(resp, HttpServletResponse.SC_OK, user);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
