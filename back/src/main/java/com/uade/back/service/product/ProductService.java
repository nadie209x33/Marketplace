package com.uade.back.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.catalog.ProductResponse;
import com.uade.back.entity.Inventario;
import com.uade.back.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final InventarioRepository inventarioRepository;
    @Transactional
    public ProductResponse getById(Integer id) {
        Inventario inventario = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return ProductResponse.builder()
                .id(inventario.getItemId())
                .name(inventario.getName())
                .description(inventario.getDescription())
                .price(inventario.getPrice())
                .category(inventario.getCategoria().getCatId()) 
                .stock(inventario.getQuantity())
                .build();
    }
}
