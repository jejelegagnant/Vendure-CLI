package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class ProductListQuery implements GraphQLQuery<List<Product>> {

  // ObjectMapper are heavy, static to reuse
  private static final ObjectMapper mapper = new ObjectMapper();

  @Override
  public String getQueryPayload() {
    return "{ \"query\": \"query { products { items { name variants { price } } } }\" }";
  }

  @Override
  public List<Product> parseResponse(String jsonResponse) {
    List<Product> productList = new ArrayList<>();

    try {
      // Convert String in a three
      JsonNode rootNode = mapper.readTree(jsonResponse);

      // Navigate to the items table
      JsonNode itemsNode = rootNode.path("data").path("products").path("items");

      // Verify data format is valid
      if (itemsNode.isArray()) {
        for (JsonNode item : itemsNode) {
          String name = item.path("name").asText();

          JsonNode variants = item.path("variants");
          double price = 0.0;

          // Extract the price of the first variant
          if (variants.isArray() && !variants.isEmpty()) {
            int priceInCents = variants.get(0).path("price").asInt();
            price = priceInCents / 100.0; // Convert form cents to francs
          }

          productList.add(new Product(name, price));
        }
      }
    } catch (Exception e) {
      // Catch all exception here to keep the same signature
      throw new RuntimeException("Error during data extraction", e);
    }

    return productList;
  }
}
