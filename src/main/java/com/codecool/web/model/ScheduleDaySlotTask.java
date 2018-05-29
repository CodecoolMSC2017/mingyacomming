package com.codecool.web.model;

import java.util.List;

public class ScheduleDaySlotTask {

    private Schedule schedule;
    private List<Day> days;
    private List<SlotTask> slotTasks;

    public ScheduleDaySlotTask(Schedule schedule, List<Day> days, List<SlotTask> slotTasks) {
        this.schedule = schedule;
        this.days = days;
        this.slotTasks = slotTasks;
    }

    public List<Day> getDays() {
        return days;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<SlotTask> getSlotTasks() {
        return slotTasks;
    }
}
