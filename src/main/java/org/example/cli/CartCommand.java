package org.example.cli;

import java.util.Collections;
import java.util.List;

import picocli.CommandLine;

@CommandLine.Command(name = "cart", description = "display the user's cart")
public class CartCommand implements SubCommand {
  @CommandLine.ParentCommand private CommandParser parent;

  // TODO
  @Override
  public void execute() {
    throw new UnsupportedOperationException("this function is not implemented");
  }

  // TODO
  List<String> fetchCartItems() {

    return Collections.emptyList();
  }
}
