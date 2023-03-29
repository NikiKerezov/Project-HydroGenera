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
import Processor.Services.ProcessPackageBoardVersionHHO2020B;
import Processor.Services.ReadFromSerialPort;
import Processor.Utils.PrintDataPackage;

public class DependencyContainerService {

    public ServiceContainer populateContainer() throws Exception {

        IJsonReader jsonReader = new JsonReader();
        Setting dependencies = jsonReader.readJson("data.json");
        ServiceContainer container = new ServiceContainer();

        switch (dependencies.getLogSettings().getLogType()){
            case "Console":
                ConsoleLogger.setInstance(dependencies.getLogSettings().getLogLevel());
                container.setLogger(ConsoleLogger.getInstance());
                break;
            case "File":
                TxtLogger.setInstance(dependencies.getLogSettings().getLogLevel(), dependencies.getLogSettings().getLogPath(), 7);
                container.setLogger(TxtLogger.getInstance());
                break;
        }

        switch (dependencies.getGeneratorSetting().getUinChip()){
            case "GI2CPU28":
                ReadFromSerialPort.setInstance(ProcessPackageBoardVersionGI2CPU28.getInstance(), dependencies.getUartSetting(), PrintDataPackage.getInstance(), container.getLogger());
                container.setSerialPort(ReadFromSerialPort.getInstance());
                break;
            case "HHO2020B":
                ReadFromSerialPort.setInstance(ProcessPackageBoardVersionHHO2020B.getInstance(), dependencies.getUartSetting(), PrintDataPackage.getInstance(), container.getLogger());
                container.setSerialPort(ReadFromSerialPort.getInstance());
                break;

        }

        switch (dependencies.getLocalDataSettings().getFileFormat()){
            case "csv":
                SaveToCsv.setInstance(dependencies.getLocalDataSettings().getPath(),dependencies.getLocalDataSettings().getDataLifespan(), container.getLogger());
                container.setFileStorage(SaveToCsv.getInstance());
                break;
        }

        switch (dependencies.getCommunicationProtocol().getServer()){
            case "WebSocket":
                DataRiverCommunication.setInstance(dependencies.getServerSettings().getUrl(), container.getLogger(), dependencies.getGeneratorSetting().getId());
                container.setDataRiver(DataRiverCommunication.getInstance());
                break;
        }
        switch (dependencies.getCommunicationProtocol().getVisualisation()){
            case "WebSocket":
                container.setOnSite(OnSiteCommunication.getInstance(container.getLogger()));
                break;
        }
        return container;
    }
}
