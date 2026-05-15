package org.example.format;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.model.Product;
import org.junit.jupiter.api.Test;

public class JsonFormaterTest {

  @Test
  public void testEmptyListReturnsEmptyJsonArray() {
    JsonFormater formater = new JsonFormater();
    String result = formater.format(List.of());
    assertEquals("[]", result);
  }

  @Test
  public void testProductsAreFormattedAsJson() {
    JsonFormater formater = new JsonFormater();
    List<Product> products = List.of(new Product("Book", 15.0));

    String result = formater.format(products);

    assertTrue(result.contains("\"name\" : \"Book\""));
    assertTrue(result.contains("\"price\" : 15.0"));
    assertTrue(result.startsWith("[ {"));
  }

  @Test
  public void testNullListReturnsEmptyJsonArray() {
    JsonFormater formater = new JsonFormater();
    String result = formater.format(null);
    assertEquals("[]", result);
  }

  @Test
  public void testFormatThrowsRuntimeExceptionOnSerializationError() {
    JsonFormater formater = new JsonFormater();

    // Create an anonymous, faulty list implementation
    List<Product> poisonList =
        new java.util.AbstractList<>() {
          @Override
          public Product get(int index) {
            // Jackson will call this method during serialization, triggering the error
            throw new UnsupportedOperationException("Simulated error to force the catch block");
          }

          @Override
          public int size() {
            // Essential so that isEmpty() returns false and passes the initial check
            return 1;
          }
        };

    // Verify that the RuntimeException is correctly thrown
    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> formater.format(poisonList));

    // Ensure the exception caught is indeed the one from our catch block
    assertEquals("JSON formating error", exception.getMessage());
  }
}
