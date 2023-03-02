package Logger.Services;

import Logger.Contracts.ILogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.logging.Logger;

public class ConsoleLogger implements ILogger {

    public static ConsoleLogger instance;
    private Logger logger;

    private ConsoleLogger(int logLevel) {
        this.LOG_LEVEL = logLevel;
        this.logQueue = new java.util.LinkedList<>();
    }

    public static ConsoleLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Logger not initialized");
        }
        return instance;
    }

    public static void setInstance(int logLevel) {
        instance = new ConsoleLogger(logLevel);
    }

    private int LOG_LEVEL = 3; // TODO: get from dependencies

    private Queue<String> logQueue;

    private boolean QUEUE_IS_LOCKED = false;

    private void emptyQueue() {
        QUEUE_IS_LOCKED = true;

        while (!this.logQueue.isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(now + ": " + this.logQueue.poll());
        }

        QUEUE_IS_LOCKED = false;
    }
    public void log(String message, int level) {
        this.logQueue.add(message);

        if (!QUEUE_IS_LOCKED)
            emptyQueue();
    }

    public int getLogLevel() {
        return LOG_LEVEL;
    }

    public void setLogLevel(int newLevel) {
        LOG_LEVEL = newLevel;
    }
}
