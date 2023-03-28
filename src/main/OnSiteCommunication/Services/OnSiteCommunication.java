package OnSiteCommunication.Services;

import DataRiverCommunication.Services.DataRiverCommunication;
import DependancyContainer.Models.Setting;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import DataRiverCommunication.Contracts.IDataRiverCommunication;
import OnSiteCommunication.Contracts.IOnSiteCommunication;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OnSiteCommunication extends Observer implements IOnSiteCommunication {

    private static OnSiteCommunication instance;
    private ILogger logger;
    private final Socket socket;

    private OnSiteCommunication(ILogger logger) throws URISyntaxException {
        this.logger = logger;
        socket = IO.socket("ws://localhost:3000");
    }
    public static OnSiteCommunication getInstance(ILogger logger) throws Exception {
        if (instance == null) {
            instance = new OnSiteCommunication(logger);
        }
        return instance;
    }

    public void startServer(){
        connect();
    }
    public void connect(){
        socket.open();
    }
    @Override
    public void update(DataPackage dataPackage) throws IOException {

        sendPackage(dataPackage);
    }

    @Override
    public void sendPackage(DataPackage dataPackage) throws IOException {
        try{
            if (!socket.connected()) {
                logger.log("Socket is not connected", 1);
                return;
            }
            socket.emit("new_data",new JSONObject(dataPackage));

        }
        catch (Exception e){
            logger.log("Exception throws: " + e.getMessage(), 2);
        }
    }
    public void receiveJson(JSONObject data) {
        try {
            String filePath = "/home/hmonitor/hMonitor/config/config.json";
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                File file = new File(filePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(filePath, false);
            fileWriter.write(data.toString());
            fileWriter.flush();
            fileWriter.close();
            System.out.println("New setting received and saved to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error while saving setting to file: " + e.getMessage());
        }
    }

    public void startListening() {
        socket.on("new_setting", (data) -> {
            System.out.println("New setting received: " + data);
            JSONObject jsonData = new JSONObject(data);
            receiveJson(jsonData);
            socket.emit("new_setting", data);
        });
    }
}

