package com.codecool.web.model;

public class Task {
    private int id;
    private int userId;
    private String name;
    private String description;

    public Task(int id, int userId, String name, String description) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public Task(int userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
