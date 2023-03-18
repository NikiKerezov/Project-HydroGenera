package DataRiverCommunication.Services;

import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import DataRiverCommunication.Contracts.IDataRiverCommunication;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.IOException;
import java.net.URISyntaxException;

public class DataRiverCommunication extends Observer implements IDataRiverCommunication {
    private final Socket socket;
    private ILogger logger;
    private static DataRiverCommunication instance;

    private int generatorId;

    public static DataRiverCommunication getInstance() throws Exception {
        if (instance == null) {
            throw new IllegalStateException("DataRiverCommunication not initialized");
        }
        return instance;
    }

    public static void setInstance(String url, ILogger logger, int id) throws Exception {
        instance = new DataRiverCommunication(url, logger, id);
    }
    private DataRiverCommunication(String url, ILogger logger, int id) throws URISyntaxException {
        this.logger = logger;
        socket = IO.socket(url);
        generatorId = id;
    }
    public void connect(){
        socket.open();
    }
    @Override
    public void sendPackage(DataPackage dataPackage) throws IOException {
        try{
            if (!socket.connected()) {
                logger.log("Socket is not connected", 1);
                return;
            }
            socket.emit("new_data", new Object() {
                public int generatorId = DataRiverCommunication.this.generatorId;
                public DataPackage data = dataPackage;
            });
        }
        catch (Exception e){
            logger.log("Exception throws: " + e.getMessage(), 1);
        }
    }

    @Override
    public void update(DataPackage dataPackage) throws IOException {
        sendPackage(dataPackage);
    }
}
