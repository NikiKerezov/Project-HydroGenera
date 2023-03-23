package UpTime.Services;

import UpTime.Contracts.IUpTime;

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
        Scanner scanner1 = new Scanner("time1");
        Scanner scanner2 = new Scanner("time2");

        long previousTime1 = scanner1.nextLong();
        long previousTime2 = scanner2.nextLong();

        scanner1.close();
        scanner2.close();

        previousTime =  Math.max(previousTime1, previousTime2);
    }

    public long getPreviousTime() {
        return previousTime;
    }

    public void WriteTimeToFile() throws IOException {
        FileWriter fileWriter1 = new FileWriter("time1", false);
        FileWriter fileWriter2 = new FileWriter("time2", false);

        RuntimeMXBean runtimeMXBean = java.lang.management.ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime() / 1000;

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
