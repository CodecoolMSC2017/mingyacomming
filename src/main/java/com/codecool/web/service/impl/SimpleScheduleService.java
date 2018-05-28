package com.codecool.web.service.impl;

import com.codecool.web.dao.database.ScheduleDatabase;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService{

    private ScheduleDatabase db;

    public SimpleScheduleService(ScheduleDatabase db) {
        this.db = db;
    }

    @Override
    public int addSchedule(Schedule schedule) throws SQLException {
        return db.addSchedule(schedule);
    }

    @Override
    public void removeSchedule(int scheduleId) throws SQLException {
        db.removeSchedule(scheduleId);
    }

    @Override
    public Schedule getSchedule(int id) throws SQLException {
        return db.getSchedule(id);
    }

    @Override
    public List<Schedule> getSchedules(User user) throws SQLException {
        if (user.getRole().equals("admin")) {
            return db.getAllSchedules();
        }
        return db.getSchedules();
    }

    @Override
    public void updateSchedule(String name, int id) throws SQLException {
        db.updateSchedule(name, id);
    }

    @Override
    public List<Schedule> getUserSchedules(User user) throws SQLException {
        return db.getUserSchedule(user.getId());
    }


}
