package com.uade.back.dto.cart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Integer id;
    private List<CartItem> items;
    private Integer total;
}
