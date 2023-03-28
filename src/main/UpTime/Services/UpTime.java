package UpTime.Services;

import UpTime.Contracts.IUpTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;
import java.util.Scanner;

public class UpTime implements IUpTime {

    public static UpTime instance;

    private final long previousTime;

    public static UpTime getInstance() {
        if (instance == null) {
            instance = new UpTime();
        }
        return instance;
    }

    private UpTime() {
        try {
            File file1 = new File("time1.txt");
            File file2 = new File("time2.txt");

            Scanner scanner1 = new Scanner(file1);
            Scanner scanner2 = new Scanner(file2);

            if (file1.length() == 0) {
                FileWriter fileWriter1 = new FileWriter("time1.txt", false);
                fileWriter1.write("0");
                fileWriter1.close();
            }

            if (file2.length() == 0) {
                FileWriter fileWriter2 = new FileWriter("time2.txt", false);
                fileWriter2.write("0");
                fileWriter2.close();
            }

            long previousTime1 = Long.parseLong(scanner1.nextLine());
            long previousTime2 = scanner2.nextLong();

            System.out.println("Previous time 1: " + previousTime1);
            System.out.println("Previous time 2: " + previousTime2);

            scanner1.close();
            scanner2.close();

            previousTime = Math.max(previousTime1, previousTime2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long getPreviousTime() {
        return previousTime;
    }

    public void WriteTimeToFile() throws IOException {
        FileWriter fileWriter1 = new FileWriter("time1.txt", false);
        FileWriter fileWriter2 = new FileWriter("time2.txt", false);

        RuntimeMXBean runtimeMXBean = java.lang.management.ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime() / 1000;

        fileWriter1.write("" + uptime);
        fileWriter2.write("" + uptime);

        fileWriter1.close();
        fileWriter2.close();

        fileWriter1 = new FileWriter("overallTime1.txt", false);
        fileWriter2 = new FileWriter("overallTime2.txt", false);

        fileWriter1.write("" + (previousTime + uptime));
        fileWriter2.write("" + (previousTime + uptime));

        fileWriter1.close();
        fileWriter2.close();
    }
}
