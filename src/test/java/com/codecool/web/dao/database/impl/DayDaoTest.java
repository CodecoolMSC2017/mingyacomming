package com.codecool.web.dao.database.impl;

import com.codecool.web.model.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DayDaoTest extends AbstractTest {

    DayDao dayDao;

    @BeforeEach
    void init() throws SQLException, FileNotFoundException {
        Connection connection = getConnection();
        runInitStript(connection);

        dayDao = new DayDao(connection);
    }

    @Test
    void getDay() throws SQLException {
        assertEquals("hetfu", dayDao.getDay(1).getName());
        assertEquals(1, dayDao.getDay(1).getSchedule_id());
    }

    @Test
    void getDays() throws SQLException {
        assertEquals(2, dayDao.getDays().size());
        assertEquals("hetfu", dayDao.getDays().get(0).getName());
    }

    @Test
    void addDay() throws SQLException {
        assertEquals(2, dayDao.getDays().size());
        int id = dayDao.addDay(new Day("monday",1));
        assertEquals(3, dayDao.getDays().size());
        assertEquals("monday", dayDao.getDay(id).getName());
    }

    @Test
    void removeDay() throws SQLException {
        assertEquals(2, dayDao.getDays().size());
        assertEquals("ketto",dayDao.getDays().get(1).getName());
        Day day = dayDao.getDay(1);
        dayDao.removeDay(day);
        assertEquals(1, dayDao.getDays().size());
        assertEquals("ketto",dayDao.getDays().get(0).getName());

    }



    @Test
    void getScheduleDays() throws SQLException {
        assertEquals(2, dayDao.getScheduleDays(1).size());
        assertEquals("hetfu", dayDao.getScheduleDays(1).get(0).getName());
    }
}