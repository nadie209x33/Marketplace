package com.uade.back.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderIdRequest;
import com.uade.back.dto.order.OrderListRequest;
import com.uade.back.dto.order.OrderResponse;
import com.uade.back.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService service;

  @PostMapping
  public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PostMapping("/by-id")
  public ResponseEntity<OrderResponse> get(@RequestBody OrderIdRequest request) {
    return ResponseEntity.ok(service.getById(request));
  }

  @PostMapping("/search")
  public ResponseEntity<List<OrderResponse>> myOrders(@RequestBody OrderListRequest request) {
    return ResponseEntity.ok(service.getMyOrders(request));
  }
}
