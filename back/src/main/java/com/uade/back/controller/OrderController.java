package com.uade.back.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import com.uade.back.service.order.OrderService;
import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderResponse;

import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/v1/orders")
// @RequiredArgsConstructor
// public class OrderController {

//   private final OrderService service;

//   @PostMapping
//   public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
//     return ResponseEntity.ok(service.create(request));
//   }

//   @GetMapping("/{id}")
//   public ResponseEntity<OrderResponse> get(@PathVariable Long id) {
//     return ResponseEntity.ok(service.getById(id));
//   }

//   @GetMapping
//   public ResponseEntity<List<OrderResponse>> myOrders(
//       @RequestParam(defaultValue = "0") int page,
//       @RequestParam(defaultValue = "20") int size) {
//     return ResponseEntity.ok(service.getMyOrders(page, size));
//   }
// }
