package org.example.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.example.graphql.GraphQLQuery;
import org.example.graphql.VendureClient;
import org.example.model.ProductDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InfoCommandTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testExecutePrintsProductDetails() {
    CommandParser parser = new CommandParser();

    // Injecting mock client
    parser.setClient(
        new VendureClient("https://test.com") {
          @Override
          @SuppressWarnings("unchecked")
          public <T> T execute(GraphQLQuery<T> query) {
            ProductDetails.Variant variant =
                new ProductDetails.Variant("CAS23340", "Size 40", 6500, "IN_STOCK");
            return (T)
                new ProductDetails("34", "Allstar Sneakers", "Iconic sneaker.", List.of(variant));
          }
        });

    SubCommand command = parser.parse(new String[] {"info", "allstar-sneakers"});
    command.execute();

    String output = outContent.toString();

    // Verify key information are present
    assertTrue(
        output.toLowerCase().contains("allstar sneakers"),
        "Output should contain the product name");
    assertTrue(output.contains("CAS23340"), "Output should contain the variant SKU");
    assertTrue(output.contains("65,00"), "Output should contains the correct price");
  }
}
