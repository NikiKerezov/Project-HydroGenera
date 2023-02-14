package ServerCommunication.Services;

import EventEmitter.Observer;
import LocalData.Models.DataPackage;
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

    public void setInstance(String url, ILogger logger) throws URISyntaxException {
        this.logger = logger;
        //instance = new WebSocketConnection(url);
        //instance = new WebSocketConnection(logger);
    }

    public static WebSocketConnection getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("instance was forgotten to be initialized");
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
