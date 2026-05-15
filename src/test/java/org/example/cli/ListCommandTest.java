package org.example.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.example.graphql.VendureClient;
import org.example.graphql.GraphQLQuery;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListCommandTest {

  // --- Console Output Capturing Setup ---

  // We create a custom stream to catch what is being printed
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  public void setUpStreams() {
    // Redirect standard output to our custom stream before each test
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    // Always restore the original standard output after the test
    System.setOut(originalOut);
  }

  // --- The Tests ---

  @Test
  public void testExecutePrintsInTableFormatByDefault() {
    CommandParser parser = new CommandParser();

    // 1. Injects a mock client
    parser.setClient(
        new VendureClient("https://test.com") {
          @Override
          @SuppressWarnings("unchecked")
          public <T> T execute(GraphQLQuery<T> query) {
            // Returns mock data
            return (T) java.util.List.of(new org.example.model.Product("Clavier", 45.50));
          }
        });

    // 2. Setup the subcommand
    SubCommand command = parser.parse(new String[] {"list"});

    // 3. Act
    command.execute();

    // 4. Assert
    String output = outContent.toString();
    assertTrue(output.contains("Name"), "Output should contain the table header 'Name'");
    assertTrue(output.contains("Price"), "Output should contain the table header 'Price'");
    assertTrue(output.contains("Clavier"), "Output should display the product name");
  }

  @Test
  public void testExecutePrintsInJsonFormatWhenOptionIsProvided() {
    CommandParser parser = new CommandParser();

    // 1. Injection du faux client
    parser.setClient(
        new VendureClient("https://test.com") {
          @Override
          @SuppressWarnings("unchecked")
          public <T> T execute(GraphQLQuery<T> query) {
            return (T) java.util.List.of(new org.example.model.Product("Clavier", 45.50));
          }
        });

    SubCommand command = parser.parse(new String[] {"list", "--format", "JSON"});

    command.execute();

    String output = outContent.toString();
    assertTrue(output.startsWith("["), "JSON output should start with a bracket");
    assertTrue(
        output.contains("\"name\" : \"Clavier\"") || output.contains("\"name\":\"Clavier\""),
        "Output should contain JSON formatted product name");
  }
}
