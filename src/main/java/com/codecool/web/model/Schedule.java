package com.codecool.web.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private int id;
    private String name;
    private int userId;
    private List<Day> days;
    private boolean isPublic;

    public Schedule(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        days = new ArrayList<>();
    }

    public Schedule(String name, int userId) {
        this.name = name;
        this.userId = userId;
        days = new ArrayList<>();
    }

    public boolean getPublic() {
        return isPublic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
