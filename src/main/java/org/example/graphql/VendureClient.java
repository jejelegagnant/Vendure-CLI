package org.example.graphql;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class VendureClient {
  private final String url;
  // HTTP client for the request
  private final HttpClient httpClient;

  public VendureClient(String url) {
    this.url = url;
    this.httpClient = HttpClient.newHttpClient();
  }

  public <T> T execute(GraphQLQuery<T> query) {
    try {
      // 1. Extract the payload
      String payload = query.getQueryPayload();

      // 2. Build the request
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(this.url))
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(payload))
              .build();

      // 3. Network part
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      // 4. Delegate the extraction to the query
      return query.parseResponse(response.body());

    } catch (Exception e) {
      // Cath all exception here
      throw new RuntimeException("HTTP or networking related error", e);
    }
  }
}
