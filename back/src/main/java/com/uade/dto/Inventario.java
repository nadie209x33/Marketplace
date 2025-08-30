package com.uade.dto;

import lombok.Data;

@Data
public class Inventario {
    private final int item_ID;
    private final String description;
    private final boolean active;
    private final int quantity;
    private final int price;
    private final int cat_ID;
    private final String name;
}
