package Logger.Contracts;

import EventEmitter.Contracts.IObserver;

import java.io.IOException;

public interface ILogger {
    void log(String message, int level) throws IOException;
}
