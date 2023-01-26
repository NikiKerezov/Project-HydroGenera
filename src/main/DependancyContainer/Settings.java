package DependancyContainer;

import DependancyContainer.Contracts.IJsonReader;
import DependancyContainer.Services.Data;
import DependancyContainer.Services.JsonReader;

public class Settings {
    public static void main(String[] args) throws Exception {
        IJsonReader jsonReader = new JsonReader();
        Data data = jsonReader.readJson("C:\\Users\\Lenovo\\repoTemp\\Project-HydroGenera\\data.json");

        // print object data
        System.out.println(data.getCommunicationProtocol().getCircuit());
        System.out.println(data.getGeneratorSetting().getCellCount());
        System.out.println(data.getServerSettings().getUrl());
        System.out.println(data.getCommunicationProtocol().getServer());
        System.out.println(data.getLocalDataSettings().getDataLifespan());
    }
}
