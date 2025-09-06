package com.uade.back.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
    private Integer stock;

}
