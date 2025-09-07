package com.uade.back.service.order;

import java.util.List;
import com.uade.back.dto.order.*;

public interface OrderService {
  OrderResponse create(CreateOrderRequest request);
  OrderResponse getById(OrderIdRequest request);
  List<OrderResponse> getMyOrders(OrderListRequest request);
}
