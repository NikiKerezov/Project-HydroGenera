package Logger.Services;

import Logger.Contracts.ILogger;

import java.util.Queue;

public class ConsoleLogger implements ILogger {

    public static ConsoleLogger instance;

    private ConsoleLogger() {}

    public static ConsoleLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Logger not initialized");
        }
        return instance;
    }

    public static void setInstance() {
        instance = new ConsoleLogger();
    }

    private int LOG_LEVEL = 3; // TODO: get from dependencies

    private Queue<String> logQueue;

    private boolean QUEUE_IS_LOCKED = false;

    private void emptyQueue() {
        QUEUE_IS_LOCKED = true;

        while (!this.logQueue.isEmpty())
            System.out.println(this.logQueue.poll());

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
