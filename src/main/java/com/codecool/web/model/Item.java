package com.codecool.web.model;

public class Item {

    private int id;
    private String name;
    private int quantity;
    private String imageUrl;


    //
    // Constructor
    //
    public Item(int id, String name, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }


    //
    // Getter(s)
    //
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    //
    // Setter(s)
    //
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
