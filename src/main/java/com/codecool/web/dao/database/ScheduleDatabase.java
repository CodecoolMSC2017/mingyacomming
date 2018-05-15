package com.codecool.web.dao.database;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;

public interface ScheduleDatabase {

    void addSchedule(Schedule schedule) throws SQLException;

    void removeSchedule(Schedule schedule) throws SQLException;

    Schedule getSchedule(int id) throws SQLException;


}
