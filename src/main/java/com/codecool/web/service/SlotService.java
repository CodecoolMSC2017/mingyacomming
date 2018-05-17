package com.codecool.web.service;

import com.codecool.web.model.Slot;
import com.codecool.web.model.SlotTask;

import java.sql.SQLException;
import java.util.List;

public interface SlotService {

    int addSlot(Slot slot) throws SQLException;

    void removeSlot(Slot slot) throws SQLException;

    Slot getSlot(int id) throws SQLException;

    void updateSlot(Slot slot) throws SQLException;

    SlotTask getSlotTask() throws SQLException;

    List<Slot> getDaySlots(int id) throws SQLException;
}
