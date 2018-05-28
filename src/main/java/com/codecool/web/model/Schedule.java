package com.codecool.web.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private int id;
    private String name;
    private int userId;
    private Boolean isPublic;

    public Schedule(int id, String name, int userId, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.isPublic = isPublic;
    }

    public Schedule(String name, int userId, Boolean isPublic) {
        this.name = name;
        this.userId = userId;
        this.isPublic = isPublic;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
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
