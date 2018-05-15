package com.codecool.web.model;

public class Slot {

    private int id;
    private int time;
    private int task_id;
    private int day_id;

    public Slot(int id, int time, int task_id, int day_id) {
        this.id = id;
        this.time = time;
        this.task_id = task_id;
        this.day_id = day_id;
    }

    public int getId() {
        return id;
    }

    public int getDay_id() {
        return day_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
