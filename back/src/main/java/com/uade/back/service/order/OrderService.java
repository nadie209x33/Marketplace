package com.uade.back.service.order;

import com.uade.back.dto.order.AdminOrderDTO;
import java.util.List;

import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderDTO;
import com.uade.back.dto.order.OrderIdRequest;
import com.uade.back.dto.order.OrderResponse;

public interface OrderService {
  OrderResponse create(CreateOrderRequest request);
  OrderResponse getById(OrderIdRequest request);
  List<OrderDTO> getMyOrders();
  List<AdminOrderDTO> getAllOrdersAdmin();
    void updatePaymentStatus(Integer pagoId, String newStatus);
    void updateDeliveryStatus(Integer orderId, String newStatus);
}
