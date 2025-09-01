package com.uade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedidos {
    private Integer pedidoId;
    private Integer listId;
    private Integer userId;
    private Integer deliveryId;
    private Boolean status;
}
