package com.codecool.web.model;

public class User {

    private String name;
    private String password;
    private int id;
    private String role;

    public User(int id, String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
