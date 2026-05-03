import org.example.CartCommand;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListCommandTest {
    @Test
    public void returnsAList() {
        CartCommand command = new CartCommand();
        String[] results = command.execute();
        assertNotEquals(null, results);
    }
}
