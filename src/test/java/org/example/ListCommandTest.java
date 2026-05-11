package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    // 1. Setup: Use the parser to perfectly simulate user input and inject the 'parent'
    CommandParser parser = new CommandParser();
    SubCommand command = parser.parse(new String[] {"--url", "http://test.com", "list"});

    // 2. Act: Run the execute method
    command.execute();

    // 3. Assert: Read what was captured in our fake console
    String output = outContent.toString();

    assertTrue(output.contains("Name"), "Output should contain the table header 'Name'");
    assertTrue(output.contains("Price"), "Output should contain the table header 'Price'");
    assertTrue(output.contains("Clavier"), "Output should display the product name");
  }

  @Test
  public void testExecutePrintsInJsonFormatWhenOptionIsProvided() {
    // 1. Setup: Provide the --format JSON option to hit the other branch of the ternary operator
    CommandParser parser = new CommandParser();
    SubCommand command =
        parser.parse(new String[] {"--url", "http://test.com", "list", "--format", "JSON"});

    // 2. Act: Run the execute method
    command.execute();

    // 3. Assert: Check for JSON specific syntax
    String output = outContent.toString();

    assertTrue(output.startsWith("["), "JSON output should start with a bracket");
    assertTrue(
        output.contains("\"name\": \"Clavier\""),
        "Output should contain JSON formatted product name");
  }
}
