package Logger.Services;

import Logger.Contracts.ILogger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

public class TxtLogger implements ILogger {
    public static TxtLogger instance;

    private TxtLogger(int logLevel) {
        this.LOG_LEVEL = logLevel;
        this.logQueue = new java.util.LinkedList<>();
    }

    public static TxtLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Logger not initialized");
        }
        return instance;
    }

    public static void setInstance(int logLevel) {
        instance = new TxtLogger(logLevel);
    }

    private int LOG_LEVEL = 3; // TODO: get from dependencies
    private final String LOG_FILE_PATH = "C:\\Users\\nikol\\Desktop\\log.txt"; //TODO: get from dependencies

    private Queue<String> logQueue;

    private boolean QUEUE_IS_LOCKED = false;

    private void emptyQueue() throws IOException {
        QUEUE_IS_LOCKED = true;

        while (!this.logQueue.isEmpty()) {
            FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            fileWriter.write(now + ": " + this.logQueue.poll());
        }

        QUEUE_IS_LOCKED = false;
    }
    public void log(String message, int level) throws IOException {
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
