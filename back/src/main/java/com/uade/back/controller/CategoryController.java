package com.uade.back.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.back.dto.catalog.CategoryIdRequest;
import com.uade.back.dto.catalog.CategoryListRequest;
import com.uade.back.dto.catalog.CategoryRequest;
import com.uade.back.dto.catalog.CategoryResponse;
import com.uade.back.dto.catalog.CategoryUpdateRequest;
import com.uade.back.service.catalog.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService service;

  @PostMapping("/search")
  public ResponseEntity<List<CategoryResponse>> list(@RequestBody CategoryListRequest request) {
    return ResponseEntity.ok(service.findAll(request));
  }

  @PostMapping("/by-id")
  public ResponseEntity<CategoryResponse> get(@RequestBody CategoryIdRequest request) {
    return ResponseEntity.ok(service.findById(request));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PutMapping
  public ResponseEntity<CategoryResponse> update(@RequestBody CategoryUpdateRequest request) {
    return ResponseEntity.ok(service.update(request));
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestBody CategoryIdRequest request) {
    service.delete(request);
    return ResponseEntity.noContent().build();
  }
}
