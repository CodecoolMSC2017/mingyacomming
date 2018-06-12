package com.codecool.web.model;

public class User {

    private String name;
    private String password;
    private int id;
    private String role;
    private String email;

    public User(int id, String name, String password, String role, String email) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.role = role;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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
