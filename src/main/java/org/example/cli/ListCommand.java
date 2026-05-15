package org.example.cli;

import java.util.List;
import org.example.format.Formater;
import org.example.format.JsonFormater;
import org.example.format.OutputFormat;
import org.example.format.TableFormater;
import org.example.graphql.ProductListQuery;
import org.example.graphql.VendureClient;
import org.example.model.Product;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "Display the list of available products")
public class ListCommand implements SubCommand {

  @CommandLine.ParentCommand private CommandParser parent;

  @CommandLine.Option(names = "--format", defaultValue = "TABLE")
  private OutputFormat format;

  @Override
  public void execute() {

    String url = parent.getUrl();

    List<Product> products = fetchProducts();

    Formater<List<Product>> activeFormater =
        (this.format == OutputFormat.JSON) ? new JsonFormater() : new TableFormater();

    System.out.println(activeFormater.format(products));
  }

  List<Product> fetchProducts() {
    // 1. Client is in CommandParser
    VendureClient client = parent.getClient();

    // 2. Prepare the query
    ProductListQuery query = new ProductListQuery();

    // 3. Exécution : client handles the query and delegates
    return client.execute(query);
  }
}
