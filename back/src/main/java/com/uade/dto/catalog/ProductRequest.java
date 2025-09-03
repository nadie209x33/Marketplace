package com.uade.dto.catalog;

public record ProductRequest(String name, String description, Double price, Long categoryId, Integer stock) {}
