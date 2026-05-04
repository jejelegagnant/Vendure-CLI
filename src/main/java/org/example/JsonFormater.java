package org.example;

import java.util.List;

public class JsonFormater implements Formater {

    @Override
    public String format(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);

            // Construction manuelle du JSON
            sb.append(String.format("  { \"name\": \"%s\", \"price\": %s }", p.name(), p.price()));

            // Ajoute une virgule s'il y a un élément suivant
            if (i < products.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");

        return sb.toString();
    }
}