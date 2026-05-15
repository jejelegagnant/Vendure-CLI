package org.example.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.model.Product;

public class JsonFormater implements Formater<List<Product>> {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Override
  public String format(List<Product> products) {
    if (products == null || products.isEmpty()) {
      return "[]";
    }
    try {
      // Automated serialization with Jackson
      return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(products);
    } catch (Exception e) {
      throw new RuntimeException("JSON formating error", e);
    }
  }
}
