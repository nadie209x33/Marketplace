package com.uade.back.dto.catalog;
public record ProductSearchRequest(Long categoryId, String q, Integer page, Integer size) {}
