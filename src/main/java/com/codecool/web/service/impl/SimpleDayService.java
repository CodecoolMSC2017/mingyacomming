package com.codecool.web.service.impl;

import com.codecool.web.dao.database.DayDatabase;
import com.codecool.web.model.Day;
import com.codecool.web.service.DayService;

import java.sql.SQLException;
import java.util.List;

public class SimpleDayService implements DayService{

    private DayDatabase db;

    public SimpleDayService(DayDatabase db) {
        this.db = db;
    }

    @Override
    public int addDay(Day day) throws SQLException {
        return db.addDay(day);
    }

    @Override
    public void removeDay(Day day) throws SQLException {
        db.removeDay(day);
    }

    @Override
    public Day getDay(int id) throws SQLException {
        return db.getDay(id);
    }

    @Override
    public List<Day> getDays() throws SQLException {
        return db.getDays();
    }

    @Override
    public List<Day> getScheduleDays(int id) throws SQLException {
        return db.getScheduleDays(id);
    }

    @Override
    public void updateDay(Day day) throws SQLException {
        db.updateDays(day);
    }


}
