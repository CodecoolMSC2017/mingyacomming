package com.codecool.web.service.impl;

import com.codecool.web.dao.database.SlotDatabase;
import com.codecool.web.model.Slot;
import com.codecool.web.service.SlotService;

import java.sql.SQLException;
import java.util.List;

public class SimpleSlotService implements SlotService {

    private SlotDatabase db;

    public SimpleSlotService(SlotDatabase db) {
        this.db = db;
    }


    @Override
    public int addSlot(Slot slot) throws SQLException {
        return db.addSlot(slot);
    }

    @Override
    public void removeSlot(Slot slot) throws SQLException {
        db.removeSlot(slot);
    }

    @Override
    public Slot getSlot(int id) throws SQLException {
        return db.getSlot(id);
    }

    @Override
    public void updateSlot(Slot slot) throws SQLException {
        db.updateSlot(slot);
    }

    @Override
    public List<Slot> getDaySlots(int id) throws SQLException {
        return db.getDaySlots(id);
    }
}
