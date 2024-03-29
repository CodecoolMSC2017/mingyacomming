package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.model.Day;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao extends AbstractDao implements ScheduleDatabase {


    public ScheduleDao(Connection connection) {
        super(connection);
    }

    @Override
    public int addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT into schedules (name, user_id, is_public) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, schedule.getName());
            ps.setInt(2, schedule.getUserId());
            ps.setBoolean(3, schedule.getPublic());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt("id");
        }
    }

    @Override
    public void removeSchedule(int scheduleId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);

        DayDatabase ddb = new DayDao(connection);
        List<Day> days = ddb.getScheduleDays(scheduleId);
        for(Day day : days) {
            ddb.removeDay(day);
        }

        String daysql = "DELETE FROM days WHERE schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(daysql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, scheduleId);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
        connection.setAutoCommit(false);
        String sql = "DELETE FROM schedules WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, scheduleId);
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
                    return fetchSchedule(rs);
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
        String sql = "SELECT * FROM schedules WHERE user_id = ? ORDER BY id ASC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Schedule> schedules = new ArrayList<>();
                while (resultSet.next()) {
                    schedules.add(fetchSchedule(resultSet));
                }
                return schedules;
            }
        }
    }

    @Override
    public List<Schedule> getAllSchedules() throws SQLException {
        String sql = "SELECT * FROM schedules";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Schedule> schedules = new ArrayList<>();
                while (resultSet.next()) {
                    schedules.add(fetchSchedule(resultSet));
                }
                return schedules;
            }
        }
    }

    @Override
    public void updateSchedule(Schedule schedule) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE schedules SET name = ?, is_public = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, schedule.getName());
            statement.setBoolean(2, schedule.getPublic());
            statement.setInt(3, schedule.getId());
            executeInsert(statement);
        } catch (SQLException se) {
            connection.rollback();
            throw se;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int user_id = resultSet.getInt("user_id");
        boolean isPublic = resultSet.getBoolean("is_public");
        return new Schedule(id, name, user_id, isPublic);
    }
}
