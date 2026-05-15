package org.example.format;

import java.util.Locale;
import org.example.model.ProductDetails;

public class ProductDetailsFormater implements Formater<ProductDetails> {

  @Override
  public String format(ProductDetails details) {
    if (details == null) {
      return "Product not found.";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("=========================================\n");
    sb.append(String.format("PRODUCT: %s (ID: %s)\n", details.name().toUpperCase(), details.id()));
    sb.append("=========================================\n");
    sb.append(String.format("Description:\n%s\n\n", details.description()));
    sb.append("Available Variants:\n");
    sb.append(
        String.format("%-15s | %-30s | %-12s | %-10s\n", "SKU", "Variant Name", "Price", "Stock"));
    sb.append("-------------------------------------------------------------------------\n");

    Locale swissFrenchLocale = Locale.of("fr", "CH");
    for (ProductDetails.Variant v : details.variants()) {
      double priceInChf = v.price();

      sb.append(
          String.format(
              swissFrenchLocale,
              "%-15s | %-30s | %.2f CHF   | %-10s\n",
              v.sku(),
              v.name(),
              priceInChf,
              v.stockLevel()));
    }

    return sb.toString();
  }
}
