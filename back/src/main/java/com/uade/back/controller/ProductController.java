package com.uade.back.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.back.dto.catalog.ProductIdRequest;
import com.uade.back.dto.catalog.ProductRequest;
import com.uade.back.dto.catalog.ProductResponse;
import com.uade.back.dto.catalog.ProductSearchRequest;
import com.uade.back.dto.catalog.ProductUpdateRequest;
import com.uade.back.service.catalog.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService service;

  @PostMapping("/search")
  public ResponseEntity<List<ProductResponse>> search(@RequestBody ProductSearchRequest request) {
    return ResponseEntity.ok(service.search(request));
  }

  @PostMapping("/by-id")
  public ResponseEntity<ProductResponse> getById(@RequestBody ProductIdRequest request) {
    return ResponseEntity.ok(service.getById(request));
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PutMapping
  public ResponseEntity<ProductResponse> update(@RequestBody ProductUpdateRequest request) {
    return ResponseEntity.ok(service.update(request));
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestBody ProductIdRequest request) {
    service.delete(request);
    return ResponseEntity.noContent().build();
  }
}
