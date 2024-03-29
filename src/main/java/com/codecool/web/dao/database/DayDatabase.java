package com.codecool.web.dao.database;

import com.codecool.web.model.Day;

import java.sql.SQLException;
import java.util.List;

public interface DayDatabase {

    int addDay(Day day) throws SQLException;

    void removeDay(Day day) throws SQLException;

    Day getDay(int id) throws SQLException;

    List<Day> getDays() throws SQLException;

    List<Day> getScheduleDays(int id) throws SQLException;

    public void updateDays (Day day) throws SQLException;
}
