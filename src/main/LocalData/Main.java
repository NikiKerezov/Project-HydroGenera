package LocalData;

import LocalData.Models.DataPackage;
import LocalData.Services.SaveToCsv;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {

        DataPackage data = new DataPackage(10, "nz", 1, 2, 3, 4, 5);
        SaveToCsv.setInstance("C:\\Users\\Lenovo\\COMport", 7, ConsoleLogger.getInstance());
        SaveToCsv file = SaveToCsv.getInstance();
        file.SaveToFile(data);

        // create object mapper
        ObjectMapper mapper = new ObjectMapper();

    }
}