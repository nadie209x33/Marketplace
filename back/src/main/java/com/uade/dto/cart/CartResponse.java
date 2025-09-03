package com.uade.dto.cart;

import java.util.List;

public record CartResponse(Long id, List<Item> items, Double total) {
  public record Item(Long id, Long productId, String name, Integer quantity, Double price, Double lineTotal) {}
}
