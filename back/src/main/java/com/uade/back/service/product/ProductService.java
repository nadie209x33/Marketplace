package com.uade.back.service.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.back.dto.catalog.ProductPageResponse;
import com.uade.back.dto.catalog.ProductRequest;
import com.uade.back.dto.catalog.ProductResponse;
import com.uade.back.entity.Categoria;
import com.uade.back.entity.Image;
import com.uade.back.entity.Inventario;
import com.uade.back.repository.CategoriaRepository;
import com.uade.back.repository.InventarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final InventarioRepository inventarioRepository;
    private final CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public ProductPageResponse search(Integer categoryId, String q, int page, int size) {
        List<Integer> categoryIds = null;
        if (categoryId != null) {
            categoryIds = getAllCategoryIds(categoryId);
        }

        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        Page<Inventario> results = inventarioRepository.search(categoryIds, q, pageable);
        List<ProductResponse> products = results.getContent().stream()
                .map(this::toProductResponse)
                .collect(java.util.stream.Collectors.toList());

        return new ProductPageResponse(products, results.getTotalPages());
    }

    private List<Integer> getAllCategoryIds(Integer categoryId) {
        List<Integer> categoryIds = new java.util.ArrayList<>();
        categoryIds.add(categoryId);

        List<Categoria> children = categoriaRepository.findByParent(categoriaRepository.findById(categoryId).orElse(null));
        for (Categoria child : children) {
            categoryIds.addAll(getAllCategoryIds(child.getCatId()));
        }
        return categoryIds;
    }

    private ProductResponse toProductResponse(Inventario inventario) {
        return ProductResponse.builder()
                .id(inventario.getItemId())
                .name(inventario.getName())
                .description(inventario.getDescription())
                .price(inventario.getPrice())
                .categoryId(inventario.getCategoria().getCatId())
                .stock(inventario.getQuantity())
                .imageIds(inventario.getImages().stream().map(Image::getImgId).collect(java.util.stream.Collectors.toList()))
                .active(inventario.getActive())
                .build();
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Integer id) {
        Inventario inventario = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toProductResponse(inventario);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (request.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        Categoria categoria = categoriaRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Inventario inventario = Inventario.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice()) // Removed incorrect (int) cast
                .quantity(request.getStock())
                .active(request.getActive())
                .categoria(categoria)
                .build();

        Inventario saved = inventarioRepository.save(inventario);

        return toProductResponse(saved);
    }

    @Transactional
    public ProductResponse update(Integer id, ProductRequest request){
        if (request.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        Inventario inventario = inventarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Categoria categoria = categoriaRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        inventario.setName(request.getName());
        inventario.setDescription(request.getDescription());
        inventario.setPrice(request.getPrice());
        inventario.setQuantity(request.getStock());
        inventario.setCategoria(categoria);
        inventario.setActive(request.getActive());

        Inventario actualizado = inventarioRepository.save(inventario);

        return toProductResponse(actualizado);
    }

    @Transactional
    public void delete(Integer id){
        Inventario inventario = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El producto no existe"));
        inventarioRepository.delete(inventario);
    }  

    @Transactional(readOnly = true)
    public ProductPageResponse searchAdmin(Integer categoryId, String q, Boolean active, int page, int size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        Page<Inventario> results = inventarioRepository.searchAdmin(categoryId, q, active, pageable);
        List<ProductResponse> products = results.getContent().stream()
                .map(this::toProductResponse)
                .collect(java.util.stream.Collectors.toList());

        return new ProductPageResponse(products, results.getTotalPages());
    }
}