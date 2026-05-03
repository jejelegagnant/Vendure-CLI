import org.example.CommandParser;
import org.example.ParsedConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {
    @Test
    public void testInvalidCommandThrowsException() {
        CommandParser parser = new CommandParser();
        String[] args = {"invalid-cmd"};

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(args);
        });
    }
    @Test
    public void testUrlPriority() {
        CommandParser parser = new CommandParser();
        String envUrl = "http://default-server.ch";
        String[] args = {"--url", "http://specific-server.ch", "list"};

        ParsedConfig result = parser.parse(args, envUrl);

        assertEquals("http://specific-server.ch", result.url());
    }
    @Test
    public void testParseListSubCommand() {
        String[] args = {"list"};
        CommandParser parser = new CommandParser();
        ParsedConfig config = parser.parse(args, null);

        assertEquals("list", config.subCommand());
    }
    @Test
    public void testParameterOrder(){
        CommandParser parser = new CommandParser();
        String[] args1 = {"--url","http://specific-server.ch","list"};
        String[] args2 = {"list","--url","http://specific-server.ch"};
        ParsedConfig config1 = parser.parse(args1);
        ParsedConfig config2 = parser.parse(args2);

        assertEquals(config1,config2);
    }
    @Test
    public void testUnknownOptionThrowsException() {
        CommandParser parser = new CommandParser();
        String[] args = {"list", "--verbose"};

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(args, null);
        });
    }
    @Test
    public void testSupportedFormatJson() {
        CommandParser parser = new CommandParser();
        String[] args = {"list", "--format", "json"};
        ParsedConfig config = parser.parse(args, null);

        assertEquals("json", config.format());
    }
    @Test
    public void testUnsupportedFormatThrowsException() {
        CommandParser parser = new CommandParser();
        String[] args = {"list", "--format", "xml"};

        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(args);
        });
    }

}