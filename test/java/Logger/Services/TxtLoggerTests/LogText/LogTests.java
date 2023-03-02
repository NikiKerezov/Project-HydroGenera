package Logger.Services.TxtLoggerTests.LogText;


import Logger.Services.TxtLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogTests {
    private static TxtLogger txtLogger;

    @BeforeEach
    public void setUp() {
        TxtLogger.setInstance(3);
        txtLogger = TxtLogger.getInstance();
    }
    //TODO: test with file

    @Test
    public void test() throws IOException {
        txtLogger.log("test", 3);
    }

    @Test
    public void testWithInstanceNull() {
        txtLogger = null;
        assertThrows(RuntimeException.class, () -> {
            txtLogger.log("test", 3);
        });
    }

    @Test
    public void testWithLevelHigherThanLogLevel() throws IOException {
        txtLogger.log("test", 4);
    }
}