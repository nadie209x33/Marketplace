package com.uade.back.dto.catalog;

public record ProductResponse(Long id, String name, String description, Double price, Long categoryId, Integer stock) {}
