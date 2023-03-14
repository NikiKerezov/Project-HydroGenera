package StopWatch.Services;

import StopWatch.Contracts.IStopWatch;
import StopWatch.Contracts.IWriteElapsedTimeToFile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteElapsedTimeToFile implements IWriteElapsedTimeToFile {

    public static WriteElapsedTimeToFile instance;

    public static WriteElapsedTimeToFile getInstance() {
        if (instance == null) {
            instance = new WriteElapsedTimeToFile();
        }
        return instance;
    }

    private WriteElapsedTimeToFile() {
    }

    public void WriteTimeToFile(IStopWatch elapsedTime, IStopWatch overallTime) throws IOException {
        FileWriter fileWriter1 = new FileWriter("time1", false);
        FileWriter fileWriter2 = new FileWriter("time2", false);

        fileWriter1.write("" + elapsedTime.getElapsedTime());
        fileWriter2.write("" + elapsedTime.getElapsedTime());

        fileWriter1.close();
        fileWriter2.close();

        fileWriter1 = new FileWriter("overallTime1", false);
        fileWriter1 = new FileWriter("overallTime2", false);

        Scanner scanner1 = new Scanner("overallTime1");
        Scanner scanner2 = new Scanner("overallTime2");

        long overallTime1 = scanner1.nextLong();
        long overallTime2 = scanner2.nextLong();

        overallTime.reset();
        overallTime.start();

        fileWriter1.write("" + (overallTime1 + overallTime.getElapsedTime()));
        fileWriter2.write("" + (overallTime2 + overallTime.getElapsedTime()));

        fileWriter1.close();
        fileWriter2.close();

        scanner1.close();
        scanner2.close();
    }
}
