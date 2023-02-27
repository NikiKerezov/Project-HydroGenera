package LocalData.Services;

import EventEmitter.Observer;
import LocalData.Contracts.ISaveToFile;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveToCsv extends Observer implements ISaveToFile {
    private final String pathFiles;
    private static SaveToCsv instance;
    private final long lifespan_in_days;
    private static File directory;
    private String fileName;
    private OutputStreamWriter writer = null;
    private ILogger logger;

    //s
    public static SaveToCsv getInstance(String path, int lifespan_in_days, ILogger logger) {
        if (instance == null) {
            instance = new SaveToCsv(path, lifespan_in_days, logger);
        }
        return instance;
    }

    private SaveToCsv(String path, int lifespan_in_days, ILogger logger) {
        this.lifespan_in_days = lifespan_in_days;
        this.pathFiles = path;
        this.logger = logger;

        checkAndDeleteOldFiles(path);

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.ms");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.ms");
        String currentTimestamp = sdf.format(new Date());
        fileName = "timestamp" + currentTimestamp + ".csv";
        try {
            File file = new File(path, fileName);
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DataPackage dataPackage){
        saveToFile(dataPackage);
    }

    public void createFolder() {
        directory = new File(pathFiles);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void saveToFile(DataPackage dataPackage) {
        try {
            writer.write(CSVUtils.toCSV(dataPackage));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //parsing the timestamp from the file name and comparing it to current time
    private void checkAndDeleteOldFiles(String dirName) {
        File directory = new File(dirName);
        long currentTime = System.currentTimeMillis();
        long lifespan = currentTime - (lifespan_in_days * 24 * 60 * 60 * 1000);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.exists() && f.getName().length()==28 && f.getName().endsWith(".csv")){
                String fileName = f.getName();
                // Get the timestamp from the file name
                String timestampString = fileName.substring(9, fileName.indexOf(".csv"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                try {
                    // Convert the timestamp string to a date
                    Date timestamp = sdf.parse(timestampString);
                    if (timestamp.getTime() < lifespan) {
                        f.delete();
                    }
                } catch (ParseException e) {
                    ConsoleLogger.getInstance().log("Exception thrown: " + e.getMessage(), 1);
                }
            }
        }
    }

    public String getFileName() {
        return fileName;
    }
}
