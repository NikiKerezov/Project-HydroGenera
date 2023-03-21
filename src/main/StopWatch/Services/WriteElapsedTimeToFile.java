package StopWatch.Services;

import StopWatch.Contracts.IWriteElapsedTimeToFile;

import javax.management.MXBean;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;
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

    public long getPreviousTime() throws IOException {
        Scanner scanner1 = new Scanner("time1");
        Scanner scanner2 = new Scanner("time2");

        long previousTime1 = scanner1.nextLong();
        long previousTime2 = scanner2.nextLong();

        scanner1.close();
        scanner2.close();

        return Math.max(previousTime1, previousTime2);
    }

    public void WriteTimeToFile(long previousTime) throws IOException {
        FileWriter fileWriter1 = new FileWriter("time1", false);
        FileWriter fileWriter2 = new FileWriter("time2", false);

        RuntimeMXBean runtimeMXBean = java.lang.management.ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime();

        fileWriter1.write("" + uptime);
        fileWriter2.write("" + uptime);

        fileWriter1.close();
        fileWriter2.close();

        fileWriter1 = new FileWriter("overallTime1", false);
        fileWriter2 = new FileWriter("overallTime2", false);

        fileWriter1.write("" + (previousTime + uptime));
        fileWriter2.write("" + (previousTime + uptime));

        fileWriter1.close();
        fileWriter2.close();
    }
}
