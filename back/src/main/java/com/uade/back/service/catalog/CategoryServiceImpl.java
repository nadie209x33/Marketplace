package com.uade.back.service.catalog;

import com.uade.back.dto.catalog.*;
import com.uade.back.entity.Categoria;
import com.uade.back.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoryResponse> findAll(CategoryListRequest request) {
        List<Categoria> categorias;
        if (request.parentId() == null) {
            categorias = categoriaRepository.findByParentIsNull();
        } else {
            
            
            Categoria parent = categoriaRepository.findById(request.parentId().intValue())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + request.parentId()));
            categorias = categoriaRepository.findByParent(parent);
        }

        return categorias.stream()
                .map(this::toCategoryResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public CategoryResponse findById(CategoryIdRequest request) {
        Categoria categoria = categoriaRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.id()));
        return toCategoryResponse(categoria);
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Categoria parent = null;
        if (request.getParentId() != 0) {
            parent = categoriaRepository.findById(request.getParentId().intValue())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + request.getParentId()));
        }


        Categoria newCategoria = Categoria.builder()
                .name(request.getName())
                .parent(parent)
                .build();

        Categoria savedCategoria = categoriaRepository.save(newCategoria);
        return toCategoryResponse(savedCategoria);
    }

    private CategoryResponse toCategoryResponse(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoryResponse(
                categoria.getCatId(),
                categoria.getName(),
                categoria.getParent() != null ? categoria.getParent().getCatId() : null
        );
    }

    @Override
    @Transactional
    public CategoryResponse update(CategoryUpdateRequest request) {
        Categoria category = categoriaRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.id()));

        if (request.name() != null) {
            category.setName(request.name());
        }

        if (request.parentId() != null) {
            if (request.parentId() == 0) {
                category.setParent(null);
            } else {
                Categoria parent = categoriaRepository.findById(request.parentId())
                        .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + request.parentId()));
                category.setParent(parent);
            }
        }

        Categoria savedCategory = categoriaRepository.save(category);
        return toCategoryResponse(savedCategory);
    }

    @Override
    @Transactional
    public void delete(CategoryIdRequest request) {
        if (!categoriaRepository.existsById(request.id())) {
            throw new RuntimeException("Category not found with id: " + request.id());
        }
        categoriaRepository.deleteById(request.id());
    }
}
