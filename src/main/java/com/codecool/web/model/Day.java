package com.codecool.web.model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private int id;
    private int user_id;
    private String name;
    private List<Slot> slots = new ArrayList<>();

    public Day(int id, String name, int user_id) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
    }

    public Day(String name, int user_id) {
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
    }

    public List<Slot> getSlots() {
        return slots;
    }
}
