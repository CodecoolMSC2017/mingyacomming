package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao extends AbstractDao implements ScheduleDatabase {


    ScheduleDao(Connection connection) {
        super(connection);
    }

    @Override
    public int addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT into schedules (name, user_id) VALUES(?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, schedule.getName());
            ps.setInt(2, schedule.getUserId());
            ResultSet resultSet = ps.executeQuery();
            return resultSet.getInt("id");
        }
    }

    @Override
    public void removeSchedule(Schedule schedule) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM schedules WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, schedule.getId());
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
    public Schedule getSchedule(int id) throws SQLException {
        String sql = "SELECT * FROM schedules WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Schedule(rs.getInt(1), rs.getString(2),rs.getInt(3));
                }
                return null;
            }
        }
    }

    @Override
    public List<Schedule> getSchedules() throws SQLException {
        String sql = "SELECT * FROM schedules";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public List<Schedule> getUserSchedule(int id) throws SQLException {
        String sql = "SELECT * FROM schedules WHERE user_id = ?";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int user_id = resultSet.getInt("user_id");
        return new Schedule(id, name, user_id);
    }
}
