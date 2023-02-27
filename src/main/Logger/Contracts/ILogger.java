package Logger.Contracts;

import java.io.IOException;

public interface ILogger {
    void log(String message, int level) throws IOException;
}
