package com.codecool.web.service;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    int addSchedule(Schedule schedule) throws SQLException;

    void removeSchedule(int scheduleId) throws SQLException;

    Schedule getSchedule(int id) throws SQLException;

    List<Schedule> getSchedules(User user) throws SQLException;

    void updateSchedule(Schedule schedule) throws SQLException;

    List<Schedule> getUserSchedules(User user) throws SQLException;
}
