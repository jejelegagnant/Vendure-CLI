package org.example.format;

import org.example.model.Product;

import java.util.List;

public interface Formater {
  String format(List<Product> products);
}
