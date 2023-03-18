package OnSiteCommunication.Services;

import DataRiverCommunication.Services.DataRiverCommunication;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import DataRiverCommunication.Contracts.IDataRiverCommunication;
import OnSiteCommunication.Contracts.IOnSiteCommunication;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

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
}
