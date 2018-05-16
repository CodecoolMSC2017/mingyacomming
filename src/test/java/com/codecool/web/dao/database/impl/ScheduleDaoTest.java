package com.codecool.web.dao.database.impl;

import com.codecool.web.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleDaoTest extends AbstractTest {



    ScheduleDao scheduleDao;

    @BeforeEach
    void init() throws SQLException, FileNotFoundException {
        Connection connection = getConnection();
        runInitStript(connection);
        scheduleDao = new ScheduleDao(connection);
    }

    @Test
    void addSchedule() throws SQLException {
        Schedule schedule = new Schedule("test", 1);
        scheduleDao.addSchedule(schedule);
    }

    @Test
    void removeSchedule() throws SQLException {
        Schedule schedule = new Schedule("test", 1);
        scheduleDao.addSchedule(schedule);
        scheduleDao.removeSchedule(3);
        assertEquals(2, scheduleDao.getSchedules().size());
        assertEquals("hard", scheduleDao.getSchedule(2).getName());
    }

    @Test
    void getSchedule() throws SQLException {
        assertEquals("hard", scheduleDao.getSchedule(2).getName());
    }

    @Test
    void getSchedules() throws SQLException {
        assertEquals(2, scheduleDao.getSchedules().size());
        assertEquals("hard", scheduleDao.getSchedules().get(1).getName());
    }

    @Test
    void getUserSchedule() throws SQLException {
        assertEquals(2, scheduleDao.getUserSchedule(1).size());
        assertEquals("hard", scheduleDao.getUserSchedule(1).get(1).getName());
    }
}