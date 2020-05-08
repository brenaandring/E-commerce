package com.brena.ecommerce.exception;

import com.brena.ecommerce.models.Item;

public class NotEnoughItemsInStockException extends Exception{

    private static final String DEFAULT_MESSAGE = "Sorry! This item is out of stock";

    public NotEnoughItemsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughItemsInStockException(Item item) {
        super(String.format("Not enough %s items in stock. Only %d left", item.getTitle(), item.getQuantity()));
    }
}
