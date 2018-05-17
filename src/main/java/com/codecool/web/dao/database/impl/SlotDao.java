package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.model.Slot;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDao extends AbstractDao implements SlotDatabase {

    public SlotDao(Connection connection) {
        super(connection);
    }

    @Override
    public int addSlot(Slot slot) throws SQLException {
        String sql = "INSERT into slots (time, task_id, day_id) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, slot.getTime());
            ps.setInt(2, slot.getTask_id());
            ps.setInt(3, slot.getDay_id());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt("id");
        }
    }

    @Override
    public void removeSlot(Slot slot) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "DELETE FROM slots WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, slot.getId());
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
    public Slot getSlot(int id) throws SQLException {
        String sql = "SELECT * FROM slots WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Slot(rs.getInt("id"), rs.getInt("time"),rs.getInt("task_id"), rs.getInt("day_id"));
                }
                return null;
            }
        }
    }

    @Override
    public List<Slot> getDaySlots(int id) throws SQLException {
        String sql = "SELECT * FROM slots WHERE day_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                List<Slot> slots = new ArrayList<>();
                while (resultSet.next()) {
                    slots.add(fetchSchedule(resultSet));
                }
                return slots;
            }
        }
    }

    @Override
    public void addTaskToSlot(int taskId, int slotId) throws SQLException {
        String sql = "UPDATE slots SET task_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.setInt(2, slotId);
            statement.executeUpdate();
        } catch (SQLException se) {
            throw se;
        }
    }

    @Override
    public void updateSlot(int id, int time) throws SQLException {
        String sql = "UPDATE slots SET time = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, time);
            statement.setInt(3, getSlot(id).getTask_id());
            statement.setInt(4, getSlot(id).getDay_id());
            executeInsert(statement);
        } catch (SQLException se) {
            throw se;
        }
    }


    private Slot fetchSchedule(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int time = resultSet.getInt("time");
        int task_id = resultSet.getInt("task_id");
        int day_id = resultSet.getInt("day_id");
        return new Slot(id, time, task_id, day_id);
    }
}
