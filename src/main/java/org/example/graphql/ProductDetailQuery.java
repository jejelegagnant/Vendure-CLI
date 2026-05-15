package org.example.graphql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.model.ProductDetails;

public class ProductDetailQuery implements GraphQLQuery<ProductDetails> {
  private static final ObjectMapper mapper = new ObjectMapper();
  private final String slug;

  public ProductDetailQuery(String slug) {
    this.slug = slug;
  }

  @Override
  public String getQueryPayload() {
    return String.format(
        "{ \"query\": \"query { product(slug: \\\"%s\\\") { id name description variants { sku name"
            + " price stockLevel } } }\" }",
        slug);
  }

  @Override
  public ProductDetails parseResponse(String jsonResponse) {
    try {
      JsonNode root = mapper.readTree(jsonResponse);
      JsonNode p = root.path("data").path("product");
      List<ProductDetails.Variant> variants =
          mapper.convertValue(p.path("variants"), new TypeReference<>() {});
      return new ProductDetails(
          p.path("id").asText(), p.path("name").asText(), p.path("description").asText(), variants);

    } catch (Exception e) {
      throw new RuntimeException("Parsing error", e);
    }
  }
}
