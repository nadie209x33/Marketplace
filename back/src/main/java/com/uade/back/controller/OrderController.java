package com.uade.back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.back.dto.order.CreateOrderRequest;
import com.uade.back.dto.order.OrderIdRequest;
import com.uade.back.dto.order.OrderListRequest;
import com.uade.back.dto.order.OrderResponse;
import com.uade.back.dto.order.UpdateDeliveryStatusRequest;
import com.uade.back.dto.order.UpdatePaymentRequest;
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

  @PatchMapping("/payment/{pagoId}")
  public ResponseEntity<Void> updatePayment(
      @PathVariable Integer pagoId,
      @RequestBody UpdatePaymentRequest request) {
    service.updatePaymentStatus(pagoId, request.getNewStatus());
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{orderId}/delivery-status")
  public ResponseEntity<Void> updateDeliveryStatus(
      @PathVariable Integer orderId,
      @RequestBody UpdateDeliveryStatusRequest request) {
    service.updateDeliveryStatus(orderId, request.getNewStatus());
    return ResponseEntity.noContent().build();
  }
}
