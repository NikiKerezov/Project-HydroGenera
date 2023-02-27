import DependancyContainer.Contracts.IJsonReader;
import DependancyContainer.Services.Data;
import DependancyContainer.Services.JsonReader;
import EventEmitter.EventEmitter;
import LocalData.Services.SaveToCsv;
import Logger.Services.ConsoleLogger;
import Processor.Services.ProcessPackageBoardVersionGI2CPU28;
import Processor.Services.ReadFromSerialPort;
import Processor.Utils.PrintDataPackage;
import ServerCommunication.Services.WebSocketConnection;

public class Startup {
//    private static SaveToCsv saveToCsv;

    public static void main(String[] args) throws Exception {
        IJsonReader jsonReader = new JsonReader();
        Data dependencies = jsonReader.readJson("C:\\Users\\Lenovo\\repoTemp\\Project-HydroGenera\\data.json");

        switch (dependencies.getGeneratorSetting().getUinChip()){
            case "A1":
                ReadFromSerialPort.setInstance(ProcessPackageBoardVersionGI2CPU28.getInstance(), dependencies.getUartSetting(), PrintDataPackage.getInstance());
                break;
        }

        switch (dependencies.getLocalDataSettings().getFileFormat()){
            case "csv":
                SaveToCsv saveToCsv = SaveToCsv.getInstance(dependencies.getLocalDataSettings().getPath(),dependencies.getLocalDataSettings().getDataLifespan(), ConsoleLogger.getInstance());
                EventEmitter.getInstance().attach(saveToCsv);
                break;
        }

        switch (dependencies.getCommunicationProtocol().getServer()){
            case "WebSocket":
                WebSocketConnection webSocketConnection = WebSocketConnection.getInstance(dependencies.getServerSettings().getUrl(), ConsoleLogger.getInstance());
                //WebServer.setInstance();
                break;
        }

        switch (dependencies.getLogSettings().getLogType()){
            case "Console":
                ConsoleLogger.setInstance(dependencies.getLogSettings().getLogLevel());
                break;
        }

//        EventEmitter.getInstance().attach(saveToCsv);
//        EventEmitter.getInstance().attach(WebSocketConnection.getInstance());
        EventEmitter.getInstance().attach(WebServer.getInstance(ConsoleLogger.instance));

        Thread server = new Thread(){
            public void run(){
                try {
                   WebServer.getInstance(ConsoleLogger.instance).startServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread client = new Thread(){
            public void run(){
                try {
                   WebSocketConnection.getInstance("", ConsoleLogger.instance).connect();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        server.start();
        client.start();
        ReadFromSerialPort.getInstance().ReadAndProcess();

    }
}
