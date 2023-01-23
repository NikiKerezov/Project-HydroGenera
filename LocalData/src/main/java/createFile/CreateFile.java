package createFile;

import dataPackage.DataPackage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateFile {
//    private static int fileCount = 0;
    private static String pathFiles = "D:\\COMPortData\\";

    public static void setPathFiles(String pathFiles) {
        CreateFile.pathFiles = pathFiles;
    }

    public static void saveDataPackageToCSV(DataPackage dataPackage) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentTimestamp = sdf.format(new Date());
        String fileName = "timestamp" + currentTimestamp + ".csv";
        File directory = new File(pathFiles);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(directory, fileName);

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
            writer.write(dataPackage.toCSV());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkAndDeleteOldFiles(directory);
    }

    private static void checkAndDeleteOldFiles(File directory) {
        long currentTime = System.currentTimeMillis();
        long aWeekAgo = currentTime - (7 * 24 * 60 * 60 * 1000);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.lastModified() < aWeekAgo) {
                file.delete();
            }
        }
    }
}

