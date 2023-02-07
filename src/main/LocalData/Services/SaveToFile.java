package LocalData.Services;

import EventEmitter.EventEmitter;
import EventEmitter.Observer;
import LocalData.Contracts.ISaveToFile;
import LocalData.Models.DataPackage;

import javax.security.auth.Subject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveToFile extends Observer implements ISaveToFile {
    private String pathFiles;
    private static SaveToFile instance;
    private int lifespan_in_days;
    
    public static void setInstance(String path, int lifespan_in_days){
        instance = new SaveToFile(path, lifespan_in_days);
    }
    public static SaveToFile getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("SaveToFile is not initialized");
        }
        return instance;
    }

    private SaveToFile(String path, int lifespan_in_days) {
        this.lifespan_in_days = lifespan_in_days;
        this.pathFiles = path;
    }

    @Override
    public void update(DataPackage dataPackage){
        SaveToCsv(dataPackage);
    }

    public void SaveToCsv(DataPackage dataPackage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentTimestamp = sdf.format(new Date());
        String fileName = "timestamp" + currentTimestamp + ".csv";
        File directory = new File(pathFiles);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(directory, fileName);
        checkAndDeleteOldFiles(directory);

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
                e.printStackTrace();
            }
        }
    }
}
