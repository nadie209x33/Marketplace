package com.uade.dto.order;

import java.util.List;

public record OrderResponse(Long id, String status, Double total, List<Item> items) {
  public record Item(Long productId, String name, Integer quantity, Double price, Double lineTotal) {}
}
