package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.model.Day;
import com.codecool.web.servlet.AbstractServlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DayDao extends AbstractDao implements DayDatabase{

    DayDao(Connection connection) {
        super(connection);
    }

    @Override
    public int addDay(Day day) throws SQLException {
        String sql = "INSERT into days (name, user_id) VALUES(?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, day.getName());
            ps.setInt(2, day.getUser_id());
            ResultSet resultSet = ps.executeQuery();
            return resultSet.getInt("id");
        }
    }

    @Override
    public void removeDay(Day day) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM days WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, day.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Day getDay(int id) throws SQLException {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Day(rs.getInt(1), rs.getString(3),rs.getInt(2));
                }
                return null;
            }
        }
    }

    @Override
    public List<Day> getDays() throws SQLException {
        String sql = "SELECT * FROM days";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Day> days = new ArrayList<>();
            while (resultSet.next()) {
                days.add(fetchSchedule(resultSet));
            }
            return days;
        }
    }

    @Override
    public List<Day> getScheduleDays(int id) throws SQLException {
        String sql = "SELECT * FROM days WHERE user_id = ?";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Day> days = new ArrayList<>();
            while (resultSet.next()) {
                days.add(fetchSchedule(resultSet));
            }
            return days;
        }
    }

    private Day fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int user_id = resultSet.getInt("user_id");
        return new Day(id, name, user_id);
    }
}