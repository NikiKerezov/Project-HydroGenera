package DependancyContainer.Services;

import DataRiverCommunication.Services.DataRiverCommunication;
import DependancyContainer.Contracts.IJsonReader;
import DependancyContainer.Models.ServiceContainer;
import DependancyContainer.Models.Setting;
import LocalData.Services.SaveToCsv;
import Logger.Services.ConsoleLogger;
import Logger.Services.TxtLogger;
import OnSiteCommunication.Services.OnSiteCommunication;
import Processor.Services.ProcessPackageBoardVersionGI2CPU28;
import Processor.Services.ReadFromSerialPort;
import Processor.Utils.PrintDataPackage;

public class DependencyContainerService {

    public ServiceContainer populateContainer() throws Exception {

        IJsonReader jsonReader = new JsonReader();
        Setting dependencies = jsonReader.readJson("C:\\Users\\golqm\\Documents\\HydroGenera\\HydroGeneraSingleAEM\\Project-HydroGenera\\data.json");
        ServiceContainer container = new ServiceContainer();

        switch (dependencies.getLogSettings().getLogType()){
            case "Console":
                ConsoleLogger.setInstance(dependencies.getLogSettings().getLogLevel());
                container.setLogger(ConsoleLogger.getInstance());
                break;
            case "File":
                TxtLogger.setInstance(dependencies.getLogSettings().getLogLevel(), dependencies.getLogSettings().getLogPath());
                container.setLogger(TxtLogger.getInstance());
                break;
        }

        switch (dependencies.getGeneratorSetting().getUinChip()){
            case "A1":
                ReadFromSerialPort.setInstance(ProcessPackageBoardVersionGI2CPU28.getInstance(), dependencies.getUartSetting(), PrintDataPackage.getInstance(), ConsoleLogger.getInstance());
                container.setSerialPort(ReadFromSerialPort.getInstance());
                break;
        }

        switch (dependencies.getLocalDataSettings().getFileFormat()){
            case "csv":
                SaveToCsv.setInstance(dependencies.getLocalDataSettings().getPath(),dependencies.getLocalDataSettings().getDataLifespan(), ConsoleLogger.getInstance());
                container.setFileStorage(SaveToCsv.getInstance());
                break;
        }

        switch (dependencies.getCommunicationProtocol().getServer()){
            case "WebSocket":
                DataRiverCommunication.setInstance(dependencies.getServerSettings().getUrl(), ConsoleLogger.getInstance(), dependencies.getGeneratorSetting().getId());
                container.setDataRiver(DataRiverCommunication.getInstance());
                break;
        }
        switch (dependencies.getCommunicationProtocol().getVisualisation()){
            case "WebSocket":
                container.setOnSite(OnSiteCommunication.getInstance(ConsoleLogger.getInstance()));
                break;
        }
        return container;
    }
}
