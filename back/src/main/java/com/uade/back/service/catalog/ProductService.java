package com.uade.back.service.catalog;

import java.util.List;
import com.uade.back.dto.catalog.*;

public interface ProductService {
  List<ProductResponse> search(ProductSearchRequest request);
  ProductResponse getById(ProductIdRequest request);
  ProductResponse create(ProductRequest request);
  ProductResponse update(ProductUpdateRequest request);
  void delete(ProductIdRequest request);
}
