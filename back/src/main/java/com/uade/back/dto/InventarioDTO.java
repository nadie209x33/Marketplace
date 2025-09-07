package com.uade.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Integer itemId;
    private String description;
    private Boolean active;
    private Integer quantity;
    private double price;
    private Integer catId;
    private String name;
}
