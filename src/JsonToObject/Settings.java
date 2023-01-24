package JsonToObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Settings {
    public static void main(String[] args) throws Exception {
        // create object mapper
        ObjectMapper mapper = new ObjectMapper();

        // read JSON file and map it to Java object
        File jsonFile = new File("C:\\Users\\Lenovo\\repoTemp\\Project-HydroGenera\\data.json");
        Data data = mapper.readValue(jsonFile, Data.class);

        // print object data
        System.out.println(data.getCommunicationProtocol().getCircuit());
        System.out.println(data.getGeneratorSetting().getCellCount());
        System.out.println(data.getServerSettings().getUrl());
    }
}
