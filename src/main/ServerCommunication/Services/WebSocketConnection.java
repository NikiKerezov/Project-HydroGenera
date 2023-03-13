package ServerCommunication.Services;

import DependancyContainer.Services.Data;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import LocalData.Services.SaveToCsv;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import ServerCommunication.Contracts.IServerConnection;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.IOException;
import java.net.URISyntaxException;

public class WebSocketConnection extends Observer implements IServerConnection {

    private final Socket socket;
    private ILogger logger;
    private static WebSocketConnection instance;

    private int generatorId;

    public static WebSocketConnection getInstance(String url, ILogger logger, int id) throws Exception {
        if (instance == null) {
            instance = new WebSocketConnection(url, logger, id);
        }
        return instance;
    }

    private WebSocketConnection(String url, ILogger logger, int id) throws URISyntaxException {
        this.logger = logger;
        socket = IO.socket(url);
        generatorId = id;
    }
    public void connect(){
        socket.open();
        socket.on(Socket.EVENT_CONNECT, objects -> {
            System.out.println(objects);
        });
        socket.on(Socket.EVENT_DISCONNECT, objects -> {
            System.out.println(objects);
        });
    }
    @Override
    public void sendPackage(DataPackage dataPackage) throws IOException {
        try{
            if (!socket.connected()) {
                throw new RuntimeException("Socket is not connected");
            }
            socket.emit("new_data", new Object() {
                public int generatorId = WebSocketConnection.this.generatorId;
                public DataPackage data = dataPackage;
            });
        }
        catch (Exception e){
            logger.log("Exception throws: " + e.getMessage(), 2);
        }
    }

    @Override
    public void update(DataPackage dataPackage) throws IOException {
        sendPackage(dataPackage);
    }
}
