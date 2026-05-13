package org.example.graphql;

public interface GraphQLQuery<T> {
  String getQueryPayload();

  T parseResponse(String jsonResponse);
}
