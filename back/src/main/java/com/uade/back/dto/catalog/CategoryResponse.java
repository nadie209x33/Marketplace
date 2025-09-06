package com.uade.back.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name; 
    private Long parentId;

}
