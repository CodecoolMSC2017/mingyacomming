package com.codecool.web.dao.database.impl;

import com.codecool.web.dao.database.ItemDatabase;
import com.codecool.web.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao extends AbstractDao implements ItemDatabase {

    //
    // Constructor(s)
    //
    public ItemDao(Connection connection) {
        super(connection);
    }

    //
    // Method(s)
    //
    @Override
    public void addItem(int userId, Item item) throws SQLException {
        String sql = "Select addItem(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setString(2, item.getName());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getImageUrl());
            ps.execute();
        }
    }

    @Override
    public Item getItemByItemName(int userId, String itemName) throws SQLException {
        String sql = "Select items.*" +
                " From items Inner Join users" +
                " ON items.user_id = users.id" +
                " Where users.id = ? AND items.name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, itemName);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return fetchItem(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Item> getItemsByUserId(int userId) throws SQLException {
        String sql = "Select * From items" +
                " Where user_id = ?";

        List<Item> items = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(fetchItem(rs));
                }
            }
        }
        return items;
    }

    private Item fetchItem(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("quantity"),
                resultSet.getString("image_url")
        );
    }
}
