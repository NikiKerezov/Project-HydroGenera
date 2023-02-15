package Processor;

import DependancyContainer.Models.UartSetting;
import Logger.Services.ConsoleLogger;
import Processor.Services.ProcessPackage;
import Processor.Services.ReadFromSerialPort;
import Processor.Utils.PrintDataPackage;

public class Main {
    public static void main(String[] args) {
        ConsoleLogger.setInstance(3);
        ReadFromSerialPort.setInstance(ProcessPackage.getInstance(), new UartSetting("COM7", 9600, 8, 1, 0), PrintDataPackage.getInstance());

        try {
            ReadFromSerialPort.getInstance().ReadAndProcess();
        } catch (Exception e) {
            ConsoleLogger.getInstance().log(e.getMessage(), 1);
        }
    }
}
