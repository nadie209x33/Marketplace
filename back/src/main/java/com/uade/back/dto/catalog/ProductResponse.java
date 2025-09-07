package com.uade.back.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

//public record ProductResponse(Long id, String name, String description, Double price, Long categoryId, Integer stock) {}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer categoryId;
    private Integer stock;

}
