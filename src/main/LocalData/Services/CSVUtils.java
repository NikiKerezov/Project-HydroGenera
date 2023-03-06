package LocalData.Services;

import LocalData.Models.DataPackage;

public class CSVUtils{
    public static String toCSV(DataPackage dataPackage) {
        StringBuilder sb = new StringBuilder();
        sb.append(dataPackage.getAdr()).append(",").append(dataPackage.getTimestamp()).append(",").append(dataPackage.getAmp()).append(",").append(dataPackage.getTmp()).append(",").append(dataPackage.getPwm()).append(",").append(dataPackage.getBar()).append(",").append(dataPackage.getUin()).append("\n");
        return sb.toString();
    }
}
