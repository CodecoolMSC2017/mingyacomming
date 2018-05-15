package com.codecool.web.dao.database;

import com.codecool.web.model.Slot;
import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface SlotDatabase {

    int addSlot(Slot slot) throws SQLException;

    void removeSlot(Slot slot) throws SQLException;

    Slot getSlot(int id) throws SQLException;

    List<Slot> getDaySlots(int id) throws SQLException;

    void addTaskToSlot(Task task) throws SQLException;
}
