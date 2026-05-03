import org.example.ListCommand;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListCommandTest {
    @Test
    public void returnsAList() {
        ListCommand command = new ListCommand();
        String[] results = command.execute();
        assertNotEquals(null, results);
    }
}
