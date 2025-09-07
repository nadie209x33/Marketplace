package com.uade.back.dto.catalog;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.uade.back.entity.Categoria;

import lombok.AllArgsConstructor;

//public record ProductResponse(Long id, String name, String description, Double price, Long categoryId, Integer stock) {}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductResponse{
    private Integer id;
    private String name;
    private String description;
    private double price;
    private Integer category;
    private Integer stock;
}
