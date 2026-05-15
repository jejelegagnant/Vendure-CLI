package org.example.model;

import java.util.List;

public record ProductDetails(String id, String name, String description, List<Variant> variants) {
  // sub-records for variants
  public record Variant(String sku, String name, double price, String stockLevel) {
    public Variant {
      price = price / 100;
    }
  }
}
