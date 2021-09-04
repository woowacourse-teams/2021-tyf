package com.example.tyfserver.payment.domain;

import com.example.tyfserver.payment.exception.ItemNotFoundException;

import java.util.Arrays;

public enum Item {
    ITEM_1("1000포인트 충전", 1000L),
    ITEM_3("3000포인트 충전", 3000L),
    ITEM_5("5000포인트 충전", 5000L),
    ITEM_10("10000포인트 충전", 10000L),
    ITEM_50("50000포인트 충전", 50000L),
    ITEM_100("100000포인트 충전", 100000L);

    private String itemName;
    private long itemPrice;

    Item(String itemName, long itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public static Item findItem(String itemId) {
        return Arrays.stream(Item.values())
                .filter(item -> item.name().equals(itemId))
                .findAny()
                .orElseThrow(ItemNotFoundException::new);
    }

    public String getItemName() {
        return itemName;
    }

    public long getItemPrice() {
        return itemPrice;
    }
}
