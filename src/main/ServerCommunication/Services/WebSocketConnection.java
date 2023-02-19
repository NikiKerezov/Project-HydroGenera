package ServerCommunication.Services;

import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import LocalData.Services.SaveToCsv;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import ServerCommunication.Contracts.IServerConnection;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class WebSocketConnection extends Observer implements IServerConnection {

    private final Socket socket;
    private ILogger logger;
    private static WebSocketConnection instance;

    public static WebSocketConnection getInstance(String url, ILogger logger) throws Exception {
        if (instance == null) {
            instance = new WebSocketConnection(url, logger);
        }
        return instance;
    }

    private WebSocketConnection(String url, ILogger logger) throws URISyntaxException {
        this.logger = logger;
        socket = IO.socket(url);
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
    public void sendPackage(DataPackage dataPackage) {
        try{
            if (!socket.connected()) {
                throw new RuntimeException("Socket is not connected");
            }
            socket.emit("new_data", dataPackage);
        }
        catch (Exception e){
            ConsoleLogger.getInstance().log("Exception throws: " + e.getMessage(), 2);
        }
    }

    @Override
    public void update(DataPackage dataPackage) {
        sendPackage(dataPackage);
    }
}
