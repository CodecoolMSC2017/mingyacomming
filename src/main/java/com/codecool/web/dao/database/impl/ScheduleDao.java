package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.model.Schedule;

import java.sql.*;

public class ScheduleDao extends AbstractDao implements ScheduleDatabase {


    ScheduleDao(Connection connection) {
        super(connection);
    }

    @Override
    public void addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT into schedule (id, name, user_id) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, schedule.getName());
            ps.setInt(2, schedule.getId());
            ps.setInt(3, schedule.getUserId());
            ps.executeQuery();
        }
    }

    @Override
    public void removeSchedule(Schedule schedule) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM schedule WHERE id = ?";
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
        String sql = "SELECT * FROM schedule WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Schedule(rs.getInt(1), rs.getString(3),rs.getInt(2));
                }
                return null;
            }
        }
    }
}
