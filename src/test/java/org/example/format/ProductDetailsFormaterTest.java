package org.example.format;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.example.model.ProductDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDetailsFormaterTest {

  private ProductDetailsFormater formatter;

  @BeforeEach
  void setUp() {
    formatter = new ProductDetailsFormater();
  }

  @Test
  void shouldReturnNotFoundMessageWhenDetailsAreNull() {
    String result = formatter.format(null);
    assertEquals("Product not found.", result);
  }

  @Test
  void shouldIncludeHeaderAndDescriptionInOutput() {
    ProductDetails details = createSampleDetails();
    String result = formatter.format(details);

    assertAll(
        "Header and Description",
        () ->
            assertTrue(
                result.contains("PRODUCT: T-SHIRT (ID: 10)"),
                "Should contain uppercase title with ID"),
        () -> assertTrue(result.contains("A nice shirt"), "Should contain description"));
  }

  @Test
  void shouldIncludeTableHeaders() {
    ProductDetails details = createSampleDetails();
    String result = formatter.format(details);

    assertAll(
        "Table Headers",
        () -> assertTrue(result.contains("SKU")),
        () -> assertTrue(result.contains("Variant Name")));
  }

  @Test
  void shouldFormatVariantDataWithSwissLocale() {
    ProductDetails details = createSampleDetails();
    String result = formatter.format(details);

    // Focusing specifically on the formatting of the variant row
    assertAll(
        "Variant Formatting",
        () -> assertTrue(result.contains("V123")),
        () -> assertTrue(result.contains("Red Size S")),
        () ->
            assertTrue(
                result.contains("15,50 CHF"), "Price should use comma separator and CHF currency"),
        () -> assertTrue(result.contains("IN_STOCK")));
  }

  private ProductDetails createSampleDetails() {
    ProductDetails.Variant variant =
        new ProductDetails.Variant("V123", "Red Size S", 1550, "IN_STOCK");
    return new ProductDetails("10", "T-Shirt", "A nice shirt", List.of(variant));
  }
}
