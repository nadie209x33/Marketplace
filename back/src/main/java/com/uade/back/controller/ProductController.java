package com.uade.back.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import com.uade.back.dto.catalog.ProductIdRequest;
import com.uade.back.dto.catalog.ProductRequest;
import com.uade.back.dto.catalog.ProductResponse;
import com.uade.back.dto.catalog.ProductSearchRequest;
import com.uade.back.dto.catalog.ProductUpdateRequest;
import com.uade.back.service.catalog.ProductService;
=======
import com.uade.back.dto.catalog.ProductRequest;
import com.uade.back.dto.catalog.ProductResponse;
import com.uade.back.service.product.ProductService;
>>>>>>> service

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

<<<<<<< HEAD
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
=======
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
    public ResponseEntity<ProductResponse> get(@PathVariable Integer id) {
    return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Integer id, @RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
    return ResponseEntity.noContent().build();
    }
>>>>>>> service
}
