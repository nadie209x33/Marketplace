package com.uade.back.service.order;

import java.util.List;

import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderDTO;
import com.uade.back.dto.order.OrderIdRequest;
import com.uade.back.dto.order.OrderResponse;
import com.uade.back.dto.order.RetryPaymentRequest;

public interface OrderService {
  OrderResponse create(CreateOrderRequest request);
  OrderResponse getById(OrderIdRequest request);
  List<OrderDTO> getMyOrders();
  List<OrderDTO> getAllOrders();
    void updatePaymentStatus(Integer pagoId, String newStatus);
    void updateDeliveryStatus(Integer orderId, String newStatus);
    OrderResponse retryPayment(Integer orderId, RetryPaymentRequest request);
    List<com.uade.back.dto.order.PaymentDTO> getOrderPayments(Integer orderId);
}
