package com.uade.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.service.catalog.CategoryService;
import com.uade.dto.catalog.CategoryRequest;
import com.uade.dto.catalog.CategoryResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService service;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> list(@RequestParam(required = false) Long parentId) {
    return ResponseEntity.ok(service.findAll(parentId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
    return ResponseEntity.ok(service.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
