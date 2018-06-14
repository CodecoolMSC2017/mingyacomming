package com.codecool.web.service;

import com.codecool.web.model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    //
    // Getter(s)
    //
    Item getItemByItemName(int userId, String itemName) throws SQLException;

    List<Item> getItemsByUserId(int userId) throws SQLException;


    //
    // Method(s)
    //
    void addItem(int userId, Item item) throws SQLException;

    void removeItem(Item item);
}
