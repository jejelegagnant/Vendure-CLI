package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class CommandParserTest {

  @Test
  public void testParseListSubCommandReturnsListCommand() {
    CommandParser parser = new CommandParser();
    SubCommand command = parser.parse(new String[] {"list"});

    assertInstanceOf(ListCommand.class, command);
  }

  @Test
  public void testParseCartSubCommandReturnsCartCommand() {
    CommandParser parser = new CommandParser();
    SubCommand command = parser.parse(new String[] {"cart"});

    assertInstanceOf(CartCommand.class, command);
  }

  @Test
  public void testUnknownSubCommandThrowsPicocliException() {
    CommandParser parser = new CommandParser();

    assertThrows(
        CommandLine.UnmatchedArgumentException.class,
        () -> {
          parser.parse(new String[] {"invalid-cmd"});
        });
  }

  @Test
  public void testMissingSubCommandThrowsIllegalArgumentException() {
    CommandParser parser = new CommandParser();

    String[] args = {"--url", "http://example.com"};

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          parser.parse(args);
        });
  }

  @Test
  public void testUnknownOptionThrowsUnmatchedArgumentException() {
    CommandParser parser = new CommandParser();

    String[] args = {"--format", "JSON"};

    assertThrows(
        picocli.CommandLine.UnmatchedArgumentException.class,
        () -> {
          parser.parse(args);
        });
  }

  @Test
  public void testUrlOptionIsParsedCorrectly() {
    CommandParser parser = new CommandParser();
    parser.parse(new String[] {"--url", "http://test.ch", "list"});

    assertEquals("http://test.ch", parser.getUrl());
  }
}
