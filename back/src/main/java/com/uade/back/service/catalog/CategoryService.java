package com.uade.back.service.catalog;

import java.util.List;
import com.uade.back.dto.catalog.*;

public interface CategoryService {
  List<CategoryResponse> findAll(CategoryListRequest request);
  CategoryResponse findById(Integer id);
  CategoryResponse create(CategoryRequest request);
  CategoryResponse update(CategoryUpdateRequest request);
  void delete(Integer id);
}
