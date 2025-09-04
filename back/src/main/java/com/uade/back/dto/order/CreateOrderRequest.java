package com.uade.back.dto.order;

public record CreateOrderRequest(Long addressId, String paymentMethod) {}
