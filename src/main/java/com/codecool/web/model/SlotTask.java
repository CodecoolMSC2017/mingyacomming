package com.codecool.web.model;

public class SlotTask {

    private Task task;
    private Slot slot;

    public SlotTask(Task task, Slot slot) {
        this.task = task;
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }

    public Task getTask() {
        return task;
    }
}
