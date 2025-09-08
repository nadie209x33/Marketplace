package com.uade.back.service.order;

import java.util.List;

import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderIdRequest;
import com.uade.back.dto.order.OrderListRequest;
import com.uade.back.dto.order.OrderResponse;

public interface OrderService {
  OrderResponse create(CreateOrderRequest request);
  OrderResponse getById(OrderIdRequest request);
  List<OrderResponse> getMyOrders(OrderListRequest request);
    void updatePaymentStatus(Integer pagoId, String newStatus);
}
