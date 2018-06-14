package com.codecool.web.service.impl;

import com.codecool.web.dao.database.ItemDatabase;
import com.codecool.web.model.Item;
import com.codecool.web.service.ItemService;

import java.sql.SQLException;
import java.util.List;

public class SimpleItemService implements ItemService {

    private ItemDatabase itemDao;

    public SimpleItemService(ItemDatabase itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Item getItemByItemName(int userId, String itemName) throws SQLException {
        return itemDao.getItemByItemName(userId, itemName);
    }

    @Override
    public List<Item> getItemsByUserId(int userId) throws SQLException {
        return itemDao.getItemsByUserId(userId);
    }

    @Override
    public void addItem(int userId, Item item) throws SQLException {
        itemDao.addItem(userId, item);
    }

    @Override
    public void removeItem(Item item) {

    }
}
