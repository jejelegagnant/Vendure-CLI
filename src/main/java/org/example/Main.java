package org.example;

import org.example.cli.CommandParser;
import org.example.cli.SubCommand;

public class Main {
  static void main(String[] args) {
    try {
      CommandParser parser = new CommandParser();
      SubCommand command = parser.parse(args);
      command.execute();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
