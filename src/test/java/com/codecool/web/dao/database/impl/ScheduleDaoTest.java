package com.codecool.web.dao.database.impl;

import com.codecool.web.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
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
        Schedule schedule = new Schedule("test", 1, false);
        scheduleDao.addSchedule(schedule);
    }

    @Test
    void removeSchedule() throws SQLException {
        Schedule schedule = new Schedule("test", 1, false);
        scheduleDao.addSchedule(schedule);
        scheduleDao.removeSchedule(3);
        assertEquals(2, scheduleDao.getSchedules().size());
        assertEquals("hard", scheduleDao.getSchedule(2).getName());
        scheduleDao.removeSchedule(1);
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

    @Test
    void updateSchedule() throws SQLException {
        Schedule schedule = scheduleDao.getSchedule(1);
        String originalName = schedule.getName();
        scheduleDao.updateSchedule(new Schedule(schedule.getId(),"alma", 1, false));
        assertEquals("alma", scheduleDao.getSchedule(1).getName());

    }
}