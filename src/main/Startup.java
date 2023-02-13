import DependancyContainer.Contracts.IJsonReader;
import DependancyContainer.Services.Data;
import DependancyContainer.Services.JsonReader;
import EventEmitter.EventEmitter;
import LocalData.Services.SaveToCsv;
import Processor.Services.ProcessPackage;
import Processor.Services.ReadFromSerialPort;
import ServerCommunication.Services.WebSocketConnection;
import WebServer.Services.WebServer;

public class Startup {
    public static void main(String[] args) throws Exception {
        IJsonReader jsonReader = new JsonReader();
        Data dependencies = jsonReader.readJson("C:\\Users\\golqm\\Documents\\HydroGenera\\HydroGeneraSingleAEM\\Project-HydroGenera\\data.json");

        switch (dependencies.getGeneratorSetting().getUinChip()){
            case "A1":
                ReadFromSerialPort.setInstance(ProcessPackage.getInstance(), dependencies.getUartSetting());
                break;
        }

        switch (dependencies.getLocalDataSettings().getFileFormat()){
            case "csv":
                SaveToCsv.setInstance(dependencies.getLocalDataSettings().getPath(),dependencies.getLocalDataSettings().getDataLifespan());
                break;
        }

        switch (dependencies.getCommunicationProtocol().getServer()){
            case "WebSocket":
                WebSocketConnection.setInstance(dependencies.getServerSettings().getUrl());
                WebServer.setInstance();
                break;
        }

        EventEmitter.getInstance().attach(SaveToCsv.getInstance());
        EventEmitter.getInstance().attach(WebSocketConnection.getInstance());
        EventEmitter.getInstance().attach(WebServer.getInstance());

        Thread server = new Thread(){
            public void run(){
                try {
                    WebServer.getInstance().startServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread client = new Thread(){
            public void run(){
                try {
                    WebSocketConnection.getInstance().connect();
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
