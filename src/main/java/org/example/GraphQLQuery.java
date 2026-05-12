package org.example;

public interface GraphQLQuery<T> {
  String getQueryPayload();

  T parseResponse(String jsonResponse);
}
