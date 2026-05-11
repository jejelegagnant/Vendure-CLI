package org.example;

import java.util.List;

public class TableFormater implements Formater {

  @Override
  public String format(List<Product> products) {
    if (products == null || products.isEmpty()) {
      return "No products found.";
    }

    StringBuilder sb = new StringBuilder();

    // En-tête du tableau
    sb.append(String.format("%-20s | %-10s\n", "Name", "Price"));
    sb.append("-----------------------------------\n");

    // Lignes de données
    for (Product p : products) {
      sb.append(String.format("%-20s | %.2f CHF\n", p.name(), p.price()));
    }

    return sb.toString();
  }
}
