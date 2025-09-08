package com.uade.back.dto.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CategoryRequest{
    @NotBlank(message = "La categoria necesita un nombre")
    private String name;
    @NotNull(message = "Si la categoria no tiene padre use '0'")
    private Integer parentId;
}