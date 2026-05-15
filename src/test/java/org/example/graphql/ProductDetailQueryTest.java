package org.example.graphql;

import static org.junit.jupiter.api.Assertions.*;

import org.example.model.ProductDetails;
import org.junit.jupiter.api.Test;

public class ProductDetailQueryTest {

  @Test
  public void testGetQueryPayloadInjectsSlugCorrectly() {
    ProductDetailQuery query = new ProductDetailQuery("aloe-vera");

    // Verify that the slug is at the right place
    String expected =
        "{ \"query\": \"query { product(slug: \\\"aloe-vera\\\") { id name description variants { sku name price stockLevel } } }\" }";
    assertEquals(expected, query.getQueryPayload());
  }

  @Test
  public void testParseResponseConvertsJsonToProductDetails() {
    ProductDetailQuery query = new ProductDetailQuery("any-slug");

    String jsonResponse =
        """
        {
          "data": {
            "product": {
              "id": "38",
              "name": "Aloe Vera",
              "description": "Decorative Aloe vera makes a lovely house plant.",
              "variants": [
                {
                  "sku": "A44352",
                  "name": "Aloe Vera",
                  "price": 699,
                  "stockLevel": "IN_STOCK"
                }
              ]
            }
          }
        }
        """;

    ProductDetails details = query.parseResponse(jsonResponse);

    assertNotNull(details);
    assertEquals("38", details.id());
    assertEquals("Aloe Vera", details.name());
    assertEquals("Decorative Aloe vera makes a lovely house plant.", details.description());

    assertNotNull(details.variants());
    assertEquals(1, details.variants().size());

    ProductDetails.Variant variant = details.variants().getFirst();
    assertEquals("A44352", variant.sku());
    assertEquals("IN_STOCK", variant.stockLevel());

    assertEquals(6.99, variant.price());
  }

  @Test
  public void invalidResponseThrowsException() {
    ProductDetailQuery query = new ProductDetailQuery("any-slug");
    String invalidJsonResponse =
        """
              {
                "data": {
                  product: {
                    "id": "38",
                    "name": "Aloe Vera",
                    "description": "Decorative Aloe vera makes a lovely house plant.",
                    "variants": [
                      {
                        "sku": "A44352",
                        "name": "Aloe Vera",
                        "price": 699,
                        "stockLevel": "IN_STOCK"
                      }
                    ]
                  }
                }
              }
              """;
    assertThrows(RuntimeException.class, () -> query.parseResponse(invalidJsonResponse));
  }
}
