package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.model.Slot;
import com.codecool.web.model.SlotTask;
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
        String sql = "INSERT into slots (time, task_id, day_id, is_checked) VALUES(?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, slot.getTime());
            ps.setInt(2, slot.getTask_id());
            ps.setInt(3, slot.getDay_id());
            ps.setBoolean(4, false);
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
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Slot(rs.getInt("id"), rs.getInt("time"), rs.getInt("task_id"), rs.getInt("day_id"));
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
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Slot> slots = new ArrayList<>();
                while (resultSet.next()) {
                    slots.add(fetchSlot(resultSet));
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
    public void updateSlot(Slot slot) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "UPDATE slots SET time = ?, task_id = ?, day_id = ?, is_checked = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, slot.getTime());
            statement.setInt(2, slot.getTask_id());
            statement.setInt(3, slot.getDay_id());
            statement.setBoolean(4, slot.getChecked());
            statement.setInt(5, slot.getId());
            executeInsert(statement);
        } catch (SQLException se) {
            connection.rollback();
            throw se;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public List<SlotTask> getSlotTask(int id) throws SQLException {
        String sql = "Select s.id, s.time, s.task_id, s.day_id, s.is_checked, t.name, t.description, t.user_id \n" +
                "                From slots as s Inner Join tasks as t ON s.task_id = t.id WHERE day_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                List<SlotTask> slotTasks = new ArrayList<>();
                while (rs.next()) {
                    slotTasks.add(new SlotTask(
                            new Task(rs.getInt("task_id"), rs.getInt("user_id"), rs.getString("name"), rs.getString("description")),
                            fetchSlot(rs)
                    ));
                }
                return slotTasks;
            }
        }
    }


    private Slot fetchSlot(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int time = resultSet.getInt("time");
        int task_id = resultSet.getInt("task_id");
        int day_id = resultSet.getInt("day_id");
        boolean isChecked = resultSet.getBoolean("is_checked");
        return new Slot(id, time, task_id, day_id, isChecked);
    }
}
