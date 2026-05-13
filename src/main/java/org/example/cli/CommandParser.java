package org.example.cli;

import org.example.graphql.VendureClient;
import picocli.CommandLine;

@CommandLine.Command(
    name = "cli",
    description = "CLI for Vendure",
    subcommands = {ListCommand.class, CartCommand.class})
public class CommandParser {

  @CommandLine.Option(
      names = "--url",
      description = "Vendure server URL",
      defaultValue = "${env:URL}",
      scope = CommandLine.ScopeType.INHERIT)
  private String url;

  private VendureClient client;

  public SubCommand parse(String[] args, String envurl) {
    CommandLine commandLine = new CommandLine(this);
    CommandLine.ParseResult result = commandLine.parseArgs(args);
    if (!result.hasSubcommand()) {
      throw new IllegalArgumentException("Missing SubCommand");
    }
    Object commandInstance = result.subcommand().commandSpec().userObject();
    return (SubCommand) commandInstance;
  }

  public SubCommand parse(String[] args) {
    return parse(args, null);
  }

  public String getUrl() {
    return url;
  }

  public VendureClient getClient() {
    if (client == null) {
      client = new VendureClient(url);
    }
    return client;
  }
}
