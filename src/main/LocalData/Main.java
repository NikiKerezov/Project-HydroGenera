package LocalData;

import LocalData.Models.DataPackage;
import LocalData.Services.SaveToFile;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        DataPackage data = new DataPackage(10, "nz", 1, 2, 3, 4, 5);
        SaveToFile file = null;
        try {
            file = SaveToFile.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        file.SaveToCsv(data);

        // create object mapper
        ObjectMapper mapper = new ObjectMapper();

    }
}