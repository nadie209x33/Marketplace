package com.uade.back.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest{

    private Integer userId;
    private Integer productId;
    private Integer quantity;

}

