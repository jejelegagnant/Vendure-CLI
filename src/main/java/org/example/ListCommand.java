package org.example;

import picocli.CommandLine;
import java.util.List;

@CommandLine.Command(name = "list", description = "Display the list of available products")
public class ListCommand implements SubCommand {

    @CommandLine.ParentCommand
    private CommandParser parent;

    @CommandLine.Option(names = "--format", defaultValue = "TABLE")
    private OutputFormat format;

    @Override
    public void execute() {

        String url = parent.getUrl();

        List<Product> products = fetchProducts(url);

        Formater activeFormater = (this.format == OutputFormat.JSON)
                ? new JsonFormater()
                : new TableFormater();

        System.out.println(activeFormater.format(products));
    }

    List<Product> fetchProducts(String url) {
        return List.of(new Product("Clavier", 45.0));
    }
}