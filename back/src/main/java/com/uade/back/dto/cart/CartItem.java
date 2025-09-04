package com.uade.back.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer id;
    private String name;
    private Integer quantity;
    private Integer price;
    private Integer lineTotal;
}
