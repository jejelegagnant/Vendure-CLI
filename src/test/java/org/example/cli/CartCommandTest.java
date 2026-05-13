package org.example.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CartCommandTest {

  @Test
  public void testFetchCartItemsReturnsEmptyListForNow() {
    CartCommand command = new CartCommand();

    List<String> items = command.fetchCartItems();

    assertNotNull(items);
    assertTrue(items.isEmpty(), "Cart should be empty for now");
  }
}
