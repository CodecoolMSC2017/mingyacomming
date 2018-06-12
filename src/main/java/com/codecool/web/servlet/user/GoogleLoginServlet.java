package com.codecool.web.servlet.user;

import com.codecool.web.dao.database.UserDatabase;
import com.codecool.web.dao.database.impl.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.impl.GoogleAuthorizeService;
import com.codecool.web.servlet.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/glogin")
public class GoogleLoginServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(Connection connection = getConnection(req.getServletContext())) {
            UserDatabase ud = new UserDao(connection);
            GoogleAuthorizeService googleAuthorizeService = new GoogleAuthorizeService();

            String idTokenString = req.getParameter("idtoken");

            String email = googleAuthorizeService.getEmail(idTokenString);

            User user = ud.getUserByEmail(email);
            if (user == null) {
                ud.addUser(email,null, "user", email);
                user = ud.getUserByEmail(email);
            }
            req.getSession().setAttribute("user", user);
            sendMessage(resp, HttpServletResponse.SC_OK, "succesfull");

        } catch (SQLException e) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            handleSqlError(resp, e);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
