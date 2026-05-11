package org.example;

import org.example.CartCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartCommandTest {

  @Test
  public void testFetchCartItemsReturnsEmptyListForNow() {
    CartCommand command = new CartCommand();

    List<String> items = command.fetchCartItems();

    assertNotNull(items);
    assertTrue(items.isEmpty(), "Cart should be empty for now");
  }
}
