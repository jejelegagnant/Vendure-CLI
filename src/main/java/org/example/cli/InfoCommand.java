package org.example.cli;

import org.example.graphql.ProductDetailQuery;
import org.example.graphql.VendureClient;
import org.example.model.ProductDetails;
import picocli.CommandLine;

@CommandLine.Command(name = "info", description = "Display details for a single product")
public class InfoCommand implements SubCommand {
  @CommandLine.ParentCommand private CommandParser parent;

  @CommandLine.Parameters(index = "0", description = "Slug of the product you need details about")
  private String slug;

  @Override
  public void execute() {
    ProductDetailQuery query = new ProductDetailQuery(slug);
    VendureClient client = parent.getClient();
    ProductDetails productDetails = client.execute(query);
    System.out.println("Product details for " + slug);
    System.out.println(productDetails);
  }
}
