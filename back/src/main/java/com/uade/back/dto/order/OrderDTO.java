package com.uade.back.dto.order;

import com.uade.back.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private String userEmail;
    private String deliveryProvider;
    private String deliveryAddress;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}
