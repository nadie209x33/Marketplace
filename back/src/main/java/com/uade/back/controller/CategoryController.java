package com.uade.back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("/byid/{id}")
  public ResponseEntity<CategoryResponse> get(@PathVariable Integer id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
    return ResponseEntity.ok(service.create(request));
  }

  @PutMapping
  public ResponseEntity<CategoryResponse> update(@RequestBody CategoryUpdateRequest request) {
    return ResponseEntity.ok(service.update(request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
