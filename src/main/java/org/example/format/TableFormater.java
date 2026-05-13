package org.example.format;

import org.example.model.Product;

import java.util.List;
import java.util.Locale;

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
    Locale swissFrenchLocale = Locale.of("fr", "CH");
    for (Product p : products) {
      sb.append(String.format(swissFrenchLocale, "%-20s | %.2f CHF\n", p.name(), p.price()));
    }

    return sb.toString();
  }
}
