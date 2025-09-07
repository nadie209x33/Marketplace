package com.uade.back.service.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.catalog.ProductRequest;
import com.uade.back.dto.catalog.ProductResponse;
import com.uade.back.entity.Categoria;
import com.uade.back.entity.Inventario;
import com.uade.back.repository.CategoriaRepository;
import com.uade.back.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final InventarioRepository inventarioRepository;
    @Transactional
    public ProductResponse getById(Integer id) {
        Inventario inventario = inventarioRepository.findByItemId(id)
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

    private final CategoriaRepository categoriaRepository;
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Inventario inventario = Inventario.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price((int) request.getPrice()) 
                .quantity(request.getStock())
                .active(request.getActive())
                .categoria(categoria)
                .build();

        Inventario saved = inventarioRepository.save(inventario);

        return ProductResponse.builder()
                .id(saved.getItemId())
                .name(saved.getName())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .category(saved.getCategoria().getCatId())
                .stock(saved.getQuantity())
                .build();
    }
}
