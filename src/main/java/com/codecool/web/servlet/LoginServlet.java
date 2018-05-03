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
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDatabase userDao = new UserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User user = loginService.loginUser(email, password);
            req.getSession().setAttribute("user", user);
            req.getRequestDispatcher("../../webapp/userpage.jsp").forward(req,resp);
            //sendMessage(resp, HttpServletResponse.SC_OK, user);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
