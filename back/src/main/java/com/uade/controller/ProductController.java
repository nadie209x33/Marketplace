package com.uade.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.service.catalog.ProductService;
import com.uade.dto.catalog.ProductRequest;
import com.uade.dto.catalog.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService service;

  @GetMapping
  public ResponseEntity<List<ProductResponse>> search(
      @RequestParam(required = false) Long categoryId,
      @RequestParam(required = false) String q,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    return ResponseEntity.ok(service.search(categoryId, q, page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
