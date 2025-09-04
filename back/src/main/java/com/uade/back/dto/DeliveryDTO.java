package com.uade.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {
    private Integer deliveryId;
    private String provider;
    private Integer addressId;
}
