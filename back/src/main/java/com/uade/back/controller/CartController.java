package com.uade.back.controller;

import com.uade.back.dto.cart.AddItemRequest;
import com.uade.back.dto.cart.CartResponse;
import com.uade.back.dto.cart.UpdateItemRequest;
import com.uade.back.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CartResponse> addItem(
        @RequestBody AddItemRequest request
    ) {
        return ResponseEntity.ok(service.addItem(request));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateItem(
        @PathVariable Integer itemId,
        @RequestBody UpdateItemRequest request
    ) {
        return ResponseEntity.ok(service.updateItem(itemId, request));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeItem(
        @PathVariable Integer itemId
    ) {
        return ResponseEntity.ok(service.removeItem(itemId));
    }

    @DeleteMapping("/items")
    public ResponseEntity<CartResponse> clear() {
        return ResponseEntity.ok(service.clear());
    }

    @PostMapping("/cupon")
    public ResponseEntity<CartResponse> aplicarCupon(
        @RequestBody com.uade.back.dto.cart.AplicarCuponRequest request
    ) {
        return ResponseEntity.ok(service.aplicarCupon(request));
    }
}
