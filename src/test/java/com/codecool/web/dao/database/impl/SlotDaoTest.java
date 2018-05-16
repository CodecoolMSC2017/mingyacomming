package com.codecool.web.dao.database.impl;

import com.codecool.web.model.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SlotDaoTest extends AbstractTest {

    SlotDao slotDao;

    @BeforeEach
    void init() throws SQLException {
        Connection connection = getConnection();
        runInitStript(connection);
        slotDao = new SlotDao(connection);
    }

    @Test
    void addSlot() throws SQLException {
        Slot slot = new Slot(0, 20, 1, 1);
        int id = slotDao.addSlot(slot);
        assertEquals(id,slotDao.getSlot(id).getId());
    }

    @Test
    void removeSlot() {
    }

    @Test
    void getSlot() throws SQLException {
        assertEquals(6, slotDao.getSlot(1).getTime());
    }

    @Test
    void getDaySlots() throws SQLException {
        assertEquals(2, slotDao.getDaySlots(1).size());
        assertEquals(6, slotDao.getDaySlots(1).get(0).getTime());
    }

    @Test
    void addTaskToSlot() {

    }
}