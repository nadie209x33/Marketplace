package com.uade.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.service.cart.CartService;
import com.uade.dto.cart.AddItemRequest;
import com.uade.dto.cart.UpdateItemRequest;
import com.uade.dto.cart.CartResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService service;

  @GetMapping
  public ResponseEntity<CartResponse> getCart() {
    return ResponseEntity.ok(service.getCurrentCart());
  }

  @PostMapping("/items")
  public ResponseEntity<CartResponse> addItem(@RequestBody AddItemRequest request) {
    return ResponseEntity.ok(service.addItem(request));
  }

  @PatchMapping("/items/{itemId}")
  public ResponseEntity<CartResponse> updateItem(@PathVariable Long itemId, @RequestBody UpdateItemRequest request) {
    return ResponseEntity.ok(service.updateItem(itemId, request));
  }

  @DeleteMapping("/items/{itemId}")
  public ResponseEntity<CartResponse> removeItem(@PathVariable Long itemId) {
    return ResponseEntity.ok(service.removeItem(itemId));
  }

  @DeleteMapping("/items")
  public ResponseEntity<CartResponse> clear() {
    return ResponseEntity.ok(service.clear());
  }
}
