package Logger.Services.ConsoleLoggerTests;

import Logger.Services.ConsoleLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogTests {
    private static ConsoleLogger consoleLogger;
    @BeforeEach
    public void setUp() {
        ConsoleLogger.setInstance(3);
        consoleLogger = ConsoleLogger.getInstance();
    }
    @Test
    public void test() {
        consoleLogger.log("test", 3);
    }

    @Test
    public void testWithInstanceNull() {
        consoleLogger = null;
        assertThrows(RuntimeException.class, () -> {
            consoleLogger.log("test", 3);
        });
    }

    @Test
    public void testWithLevelHigherThanLogLevel() {
        consoleLogger.log("test", 4);
    }
}
