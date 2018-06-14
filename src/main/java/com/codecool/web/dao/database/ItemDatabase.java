package com.codecool.web.dao.database;

import com.codecool.web.model.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDatabase {
    void addItem(int userId, Item item) throws SQLException;

    Item getItemByItemName(int userId, String itemName) throws SQLException;

    List<Item> getItemsByUserId(int userId) throws SQLException;
}
