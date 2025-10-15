package com.uade.back.dto.catalog;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryTreeDTO {
    private Integer id;
    private String name;
    private List<CategoryTreeDTO> children;
}
