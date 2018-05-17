package com.codecool.web.dao.database;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDatabase {

    int addSchedule(Schedule schedule) throws SQLException;

    void removeSchedule(int id) throws SQLException;

    Schedule getSchedule(int id) throws SQLException;

    List<Schedule> getSchedules() throws SQLException;

    void updateSchedule(String name, int id) throws SQLException;

    List<Schedule> getUserSchedule(int id) throws SQLException;


}
