package com.codecool.web.service;

import com.codecool.web.model.Day;

import java.sql.SQLException;
import java.util.List;

public interface DayService {

    int addDay(Day day) throws SQLException;

    void removeDay(Day day) throws SQLException;

    Day getDay(int id) throws SQLException;

    List<Day> getDays() throws SQLException;

    List<Day> getScheduleDays(int id) throws SQLException;
}
