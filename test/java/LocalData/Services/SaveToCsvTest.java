package LocalData.Services;

import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

class SaveToCsvTest {
    private static SaveToCsv saveToCsv;
    private static String testFilePath = "c:/test_files/";
    private static int lifespanInDays = 30;
    private static ILogger logger;

    @BeforeAll
    static void setUp() {
        saveToCsv = SaveToCsv.getInstance(testFilePath, lifespanInDays, logger);
        saveToCsv.createFolder();
        System.out.println("createFolder");
    }

    @Test
    void testSaveToFile() {
        // empty folder
        File[] filesToDelete = new File(testFilePath).listFiles();
        for (int i = 0; i < filesToDelete.length; i++) {
            filesToDelete[i].delete();
        }

        for (int i = 0; i < 3; i++) {
            DataPackage testDataPackage = new DataPackage(10, "nz", 1, 2, 3, 4, 5);
            saveToCsv.saveToFile(testDataPackage);
            String fileName = saveToCsv.getFileName();
            System.out.println(fileName);
            File file = new File(testFilePath + fileName);


            Assertions.assertTrue(file.exists());
            File[] files = new File(testFilePath).listFiles();
            Assertions.assertTrue(files.length == 1);
        }
    }
}