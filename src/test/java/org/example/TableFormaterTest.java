package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TableFormaterTest {

  @Test
  public void testEmptyListReturnsNoProductsMessage() {
    TableFormater formatter = new TableFormater();
    String result = formatter.format(List.of());

    // Verifying the exact message expected in your TableFormatter class
    assertEquals("No products found.", result);
  }

  @Test
  public void testProductsAreFormattedAsTable() {
    TableFormater formatter = new TableFormater();
    List<Product> products = List.of(new Product("Book", 15.0), new Product("Keyboard", 45.50));

    String result = formatter.format(products);

    // 1. Header verification
    assertTrue(result.contains("Name"), "The table must contain the 'Name' column");
    assertTrue(result.contains("Price"), "The table must contain the 'Price' column");
    assertTrue(result.contains("---"), "The table must contain a separator");

    // 2. Data integration verification
    assertTrue(result.contains("Book"), "The product name must be displayed");
    assertTrue(result.contains("Keyboard"));

    // 3. Price formatting and currency verification
    assertTrue(
        result.contains("15,00 CHF"),
        "The price must be formatted with 2 decimal places and the currency");
    assertTrue(result.contains("45,50 CHF"));
  }
}
