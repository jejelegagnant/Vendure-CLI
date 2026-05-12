package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class JsonFormater implements Formater {

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
