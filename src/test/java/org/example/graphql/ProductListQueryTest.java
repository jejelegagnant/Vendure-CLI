package org.example.graphql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.example.model.Product;
import org.junit.jupiter.api.Test;

public class ProductListQueryTest {

  @Test
  public void testGetQueryPayloadProducesCorrectGraphQL() {
    ProductListQuery query = new ProductListQuery();
    String expected = "{ \"query\": \"query { products { items { name variants { price } } } }\" }";

    assertEquals(expected, query.getQueryPayload());
  }

  @Test
  public void testParseResponseConvertsJsonToProductList() {
    ProductListQuery query = new ProductListQuery();

    // Minimal JSON payload to mock server response
    String jsonResponse =
        """
        {
          "data": {
            "products": {
              "items": [
                {
                  "name": "Keyboard",
                  "variants": [ { "price": 4550 } ]
                },
                {
                  "name": "Mouse",
                  "variants": [ { "price": 2500 } ]
                }
              ]
            }
          }
        }
        """;

    List<Product> result = query.parseResponse(jsonResponse);

    assertNotNull(result);
    assertEquals(2, result.size());

    assertEquals("Keyboard", result.get(0).name());
    assertEquals(45.50, result.get(0).price(), 0.001); // Floating point margin of error

    assertEquals("Mouse", result.get(1).name());
    assertEquals(25.00, result.get(1).price(), 0.001);
  }

  @Test
  public void testMalformedResponseThrowsJson() {
    ProductListQuery query = new ProductListQuery();
    String jsonMalformedResponse =
        """
        {
          "data": {
            "products": {
              "items": [
                {
                  "name": "Key
                  board",
                  "variants": [ { "price": 4550 } ]
                }
              ]
            }
          }
        }
        """;
    assertThrows(RuntimeException.class, () -> query.parseResponse(jsonMalformedResponse));
  }

  @Test
  void shouldHandleEmptyVariantsAsZeroPrice() {
    String json =
        """
        {
          "data": {
            "products": {
              "items": [
                { "name": "Free Item", "variants": [] }
              ]
            }
          }
        }
        """;
    ProductListQuery query = new ProductListQuery();
    List<Product> result = query.parseResponse(json);
    assertEquals(1, result.size(), "The list should contain exactly one element");
    assertEquals(0.0, result.getFirst().price(), "The price should be 0.0");
  }

  @Test
  void shouldReturnEmptyListWhenItemsIsNotAnArray() {
    String json = "{ \"data\": { \"products\": { \"items\": {} } } }";
    ProductListQuery query = new ProductListQuery();
    List<Product> result = query.parseResponse(json);
    assertArrayEquals(new Product[0], result.toArray(), "The list should be empty");
  }
}
