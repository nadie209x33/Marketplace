package com.uade.back.dto.order;

import com.uade.back.entity.enums.OrderStatus;
import com.uade.back.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private String userEmail;
    private String deliveryProvider;
    private String deliveryAddress;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private String paymentMethod;
    private Double total;
    private List<OrderItemDTO> items;
    private Integer paymentId;
    private Instant orderTimestamp;
    private Instant paymentTimestamp;
}