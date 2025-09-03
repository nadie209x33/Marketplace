package com.uade.dto;

import lombok.Data;

@Data
public class CartItem {
    private final int tlist_ID;
    private final int list_ID;
    private final int item_ID;
    private final int quantity;
}
