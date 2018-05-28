package com.codecool.web.model;

public class Slot {

    private int id;
    private int time;
    private int task_id;
    private int day_id;
    private Boolean isChecked;

    public Slot(int id, int time, int task_id, int day_id, Boolean isChecked) {
        this.id = id;
        this.time = time;
        this.task_id = task_id;
        this.day_id = day_id;
        this.isChecked = isChecked;
    }

    public Slot(int id, int time, int task_id, int day_id) {
        this.id = id;
        this.time = time;
        this.task_id = task_id;
        this.day_id = day_id;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
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
