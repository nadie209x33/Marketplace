package com.uade.back.dto.catalog;
public record ProductUpdateRequest(Long id, String name, String description, Double price, Integer categoryId, Integer stock) {}
