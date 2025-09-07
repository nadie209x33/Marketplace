package com.uade.back.dto.catalog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//public record ProductRequest(String name, String description, Double price, Long categoryId, Integer stock) {}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest{
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "La descripcion es obligatoria")
    private String description;
    @Min(value = 0, message = "El precio no puede ser negativo")
    private double price;
    @NotNull(message = "La categoría es obligatoria")
    private Integer categoryId;
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    @NotNull(message = "el estado activo es obligatorio")
    private Boolean active;
}