package Processor;

import DependancyContainer.Models.UartSetting;
import Logger.Services.ConsoleLogger;
import Processor.Services.ProcessPackageBoardVersionGI2CPU28;
import Processor.Services.ReadFromSerialPort;
import Processor.Utils.PrintDataPackage;

public class Main {
    public static void main(String[] args) {
        ConsoleLogger.setInstance(3);
        try {
            ReadFromSerialPort.setInstance(ProcessPackageBoardVersionGI2CPU28.getInstance(), new UartSetting("COM7", 9600, 8, 1, 0), PrintDataPackage.getInstance(), ConsoleLogger.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            ReadFromSerialPort.getInstance().ReadAndProcess();
        } catch (Exception e) {
            ConsoleLogger.getInstance().log(e.getMessage(), 1);
        }
    }
}
