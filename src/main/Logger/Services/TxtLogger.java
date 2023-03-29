package Logger.Services;

import Logger.Contracts.ILogger;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Queue;

public class TxtLogger implements ILogger {
    public static TxtLogger instance;
    private int lifespan_in_days;
    private int LOG_LEVEL;
    private String logDirPath;
    private Queue<String> logQueue;
    private File directory;
    private OutputStreamWriter writer = null;
    private String fileName;

    private TxtLogger(int logLevel, String dirPath, int lifespan_in_days) {
        this.LOG_LEVEL = logLevel;
        this.logQueue = new java.util.LinkedList<>();
        this.logDirPath = dirPath;
        this.lifespan_in_days = lifespan_in_days;

        try {
            checkAndDeleteOldFiles(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.ms");
        String currentTimestamp = sdf.format(new Date());
        fileName = "timestamp" + currentTimestamp + ".txt";
        try {
            File file = new File(dirPath, fileName);
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TxtLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Logger not initialized");
        }
        return instance;
    }

    public static void setInstance(int logLevel, String filePath, int lifespan_in_days) {
        instance = new TxtLogger(logLevel, filePath, lifespan_in_days);
    }

    private boolean QUEUE_IS_LOCKED = false;
    public void createFolder() {
        directory = new File(logDirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void saveToFile(String message) throws IOException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            writer.write(now + ": " + this.logQueue.poll() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void checkAndDeleteOldFiles(String dirName) throws IOException {
        File directory = new File(dirName);
        long currentTime = System.currentTimeMillis();
        long lifespan = currentTime - (lifespan_in_days * 24 * 60 * 60 * 1000);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.exists() && f.getName().endsWith(".txt")){
                String fileName = f.getName();
                // Get the timestamp from the file name
                String timestampString = fileName.substring(9, fileName.indexOf(".txt"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.ms");

                try {
                    // Convert the timestamp string to a date
                    Date timestamp = sdf.parse(timestampString);
                    if (timestamp.getTime() < lifespan) {
                        f.delete();
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);                 }
            }
        }
    }

    private void emptyQueue() throws IOException {
        QUEUE_IS_LOCKED = true;

        while (!this.logQueue.isEmpty()) {
            FileWriter fileWriter = new FileWriter(logDirPath, true);

            fileWriter.close();
        }

        QUEUE_IS_LOCKED = false;
    }
    public void log(String message, int level) throws IOException {
        this.logQueue.add(message + "\n");

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
