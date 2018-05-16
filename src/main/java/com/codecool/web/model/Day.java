package com.codecool.web.model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private int id;
    private int schedule_id;
    private String name;
    private List<Slot> slots = new ArrayList<>();

    public Day(int id, String name, int schedule_id) {
        this.id = id;
        this.schedule_id = schedule_id;
        this.name = name;
    }

    public Day(String name, int schedule_id) {
        this.schedule_id = schedule_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public List<Slot> getSlots() {
        return slots;
    }
}
