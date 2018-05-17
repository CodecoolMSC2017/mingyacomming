package com.codecool.web.service;

import com.codecool.web.model.Slot;

import java.sql.SQLException;
import java.util.List;

public interface SlotService {

    int addSlot(Slot slot) throws SQLException;

    void removeSlot(Slot slot) throws SQLException;

    Slot getSlot(int id) throws SQLException;

    void updateSlot(int id, int time) throws SQLException;

    List<Slot> getDaySlots(int id) throws SQLException;
}
