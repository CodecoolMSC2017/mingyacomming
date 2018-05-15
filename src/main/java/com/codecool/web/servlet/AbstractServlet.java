package com.codecool.web.servlet;

import com.codecool.web.dto.MessageDto;
import com.codecool.web.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractServlet extends HttpServlet {

    private final ObjectMapper om = new ObjectMapper();

    public Connection getConnection(ServletContext sce) throws SQLException {
        DataSource dataSource = (DataSource) sce.getAttribute("dataSource");
        return dataSource.getConnection();
    }

    public void sendMessage(HttpServletResponse resp, int status, String message) throws IOException {
        sendMessage(resp, status, new MessageDto(message));
    }

    public void sendMessage(HttpServletResponse resp, int status, Object object) throws IOException {
        resp.setStatus(status);
        om.writeValue(resp.getOutputStream(), object);
    }

    public void handleSqlError(HttpServletResponse resp, SQLException ex) throws IOException {
        sendMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        ex.printStackTrace();
    }

    public User getUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    public String getJsonParameter(String parameter, String jsonString) throws IOException {
        JsonNode jsonNode = om.readTree(jsonString);
        return jsonNode.get(parameter).asText();
    }
}


