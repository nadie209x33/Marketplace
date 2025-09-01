package com.uade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    private Integer deliveryId;
    private String provider;
    private Integer addressId;
}
