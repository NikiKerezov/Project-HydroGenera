package Processor.Utils;

import LocalData.Models.DataPackage;

public class printDataPackage {
    static printDataPackage instance = new printDataPackage();

    private printDataPackage() {
    }

    public static printDataPackage getInstance() {
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
    }
}
