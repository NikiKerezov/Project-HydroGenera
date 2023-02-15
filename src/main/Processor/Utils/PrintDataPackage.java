package Processor.Utils;

import LocalData.Models.DataPackage;

public class PrintDataPackage {
    static PrintDataPackage instance = new PrintDataPackage();

    private PrintDataPackage() {
    }

    public static PrintDataPackage getInstance() {
        return instance;
    }

    public void printPackage(DataPackage dataPackage) {
        System.out.println("DataPackage address: " + dataPackage.getAdr());
        System.out.println("DataPackage timestamp: " + dataPackage.getTimestamp());
        System.out.println("DataPackage amp: " + dataPackage.getAmp());
        System.out.println("DataPackage tmp: " + dataPackage.getTmp());
        System.out.println("DataPackage bar: " + dataPackage.getBar());
        System.out.println("DataPackage uin: " + dataPackage.getUin());
        System.out.println("DataPackage pwm: " + dataPackage.getPwm());
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}
