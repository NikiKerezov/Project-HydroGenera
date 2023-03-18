package LocalData.Services;

import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

class SaveToCsvTest {
    private static SaveToCsv saveToCsv;
    private static String testFilePath = "c:/test_files/";
    private static int lifespanInDays = 30;
    private static ILogger logger;

    @BeforeAll
    static void setUp() throws IOException {
        saveToCsv = SaveToCsv.getInstance();
        saveToCsv.createFolder();
        System.out.println("createFolder");
    }

    //proverka dali zapisva pravilnite danni
    /*@Test
    void testValuesOfFile() throws IOException {
        // Create a test DataPackage
        DataPackage testDataPackage = new DataPackage(10, "nz", 1, 2, 3, 4, 5);

        // Call the saveToFile method
        saveToCsv.saveToFile(testDataPackage);

        // Get the name of the file that was saved
        String fileName = saveToCsv.getFileName();

        // Read the contents of the file
        String filePath = testFilePath + fileName;
        String fileContents = Files.readString(Paths.get(filePath));

        // Check if the file contents match the expected output
        String expectedOutput = "10,nz,1.0,2.0,3.0,4.0,5.0";
        assertEquals(expectedOutput, fileContents.trim());
    }*/

    @Test
    void testSaveToFile() throws IOException {
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
        //DataPackage testDataPackage = new DataPackage(10, "nz", 1, 2, 3, 4, 5);
        String fileName = saveToCsv.getFileName();
        // Read the contents of the file
        String filePath = testFilePath + fileName;
        String fileContents = Files.readString(Paths.get(filePath));

        // Check if the file contents match the expected output
        String expectedOutput = "10,nz,1.0,2.0,3.0,4.0,5.0";
    }
}