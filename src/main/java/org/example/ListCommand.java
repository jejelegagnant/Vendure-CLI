package org.example;

import java.util.List;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "Display the list of available products")
public class ListCommand implements SubCommand {

  @CommandLine.ParentCommand private CommandParser parent;

  @CommandLine.Option(names = "--format", defaultValue = "TABLE")
  private OutputFormat format;

  @Override
  public void execute() {

    String url = parent.getUrl();

    List<Product> products = fetchProducts(url);

    Formater activeFormater =
        (this.format == OutputFormat.JSON) ? new JsonFormater() : new TableFormater();

    System.out.println(activeFormater.format(products));
  }

  List<Product> fetchProducts(String url) {
    // 1. Client is in CommandParser
    VendureClient client = parent.getClient();

    // 2. Prepare the query
    ProductListQuery query = new ProductListQuery();

    // 3. Exécution : client handles the query and delegates
    return client.execute(query);
  }
}
