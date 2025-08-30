package com.uade.dto;

import lombok.Data;

@Data
public class Categoria {
    private final int cat_ID;
    private final String name;
    private final int parent_ID;
}
