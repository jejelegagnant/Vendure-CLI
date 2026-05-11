package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
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

    assertTrue(result.contains("\"name\": \"Book\""));
    assertTrue(result.contains("\"price\": 15.0"));
    assertTrue(result.startsWith("[\n"));
  }
}
