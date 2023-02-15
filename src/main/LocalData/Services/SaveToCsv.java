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
    private File file;
    private ILogger logger;
    
    public static void setInstance(String path, int lifespan_in_days, ILogger logger){
        instance = new SaveToCsv(path, lifespan_in_days, logger);
        //create new files
        instance.createFile();
        //instance.checkAndDeleteOldFiles(directory);
    }
    public static SaveToCsv getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("SaveToFile is not initialized");
        }
        return instance;
    }

    private SaveToCsv(String path, int lifespan_in_days, ILogger logger) {
        this.lifespan_in_days = lifespan_in_days;
        this.pathFiles = path;
        this.logger = logger;
    }

    @Override
    public void update(DataPackage dataPackage){
        SaveToFile(dataPackage);
    }

    public void createFile(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentTimestamp = sdf.format(new Date());
        String fileName = "timestamp" + currentTimestamp + ".csv";
        directory = new File(pathFiles);
        if (!directory.exists()) {
            directory.mkdir();
        }
        file = new File(directory, fileName);
        //checkAndDeleteOldFiles(directory);
    }

    public void SaveToFile(DataPackage dataPackage) {
        while (file.exists()) {
            String name = file.getName();
            int dotIndex = name.lastIndexOf(".");
            String base = name.substring(0, dotIndex);
            String extension = name.substring(dotIndex);
            int count = Integer.parseInt(base.substring(base.length() - 1));
            base = base.substring(0, base.length() - 1) + (count + 1);
            file = new File(directory, base + extension);
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(CSVUtils.toCSV(dataPackage));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //parsing the timestamp from the file name and comparing it to current time
    private void checkAndDeleteOldFiles(File directory) {
        long currentTime = System.currentTimeMillis();
        long lifespan = currentTime - (lifespan_in_days * 24 * 60 * 60 * 1000);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.exists() && file.getName().length()==28 && file.getName().endsWith(".csv")){
                String fileName = file.getName();
                // Get the timestamp from the file name
                String timestampString = fileName.substring(9, fileName.indexOf(".csv"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                try {
                    // Convert the timestamp string to a date
                    Date timestamp = sdf.parse(timestampString);
                    if (timestamp.getTime() < lifespan) {
                        file.delete();
                    }
                } catch (ParseException e) {
                    ConsoleLogger.getInstance().log("Exception thrown: " + e.getMessage(), 1);
                }
            }
        }
    }
}
