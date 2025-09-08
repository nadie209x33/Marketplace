package com.uade.back.dto.catalog;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Integer> imageIds;

}
