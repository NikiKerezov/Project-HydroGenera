package WebServer.Services;

import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import ServerCommunication.Services.WebSocketConnection;
import WebServer.Contracts.IWebServer;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public class WebServer extends Observer implements IWebServer {

    private static WebServer instance;
    private ILogger logger;

    private SocketIOServer server;
    private WebServer(ILogger logger) throws InterruptedException {
        this.logger = logger;
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        server = new SocketIOServer(config);
    }

    public static WebServer getInstance(ILogger logger) throws Exception {
        if (instance == null) {
            instance = new WebServer(logger);
        }
        return instance;
    }

    public void startServer(){
        server.start();
        server.addEventListener("message", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("message", data);
                if (client != null && client.isChannelOpen()) {
                    server.getBroadcastOperations().sendEvent("message", data);
                } else {
                    System.out.println("No connected clients to broadcast to.");
                }
            }
        });

        server.addConnectListener(new ConnectListener(){

            @Override
            public void onConnect(SocketIOClient socketIOClient) {

            }
        });
    }

    @Override
    public void update(DataPackage dataPackage) {
        sendPackage(dataPackage);
    }

    @Override
    public void sendPackage(DataPackage dataPackage) {
        try {
            // Check if there are any connected clients
            List<SocketIOClient> clients = (List<SocketIOClient>) server.getAllClients();
            if (clients.isEmpty()) {
                ConsoleLogger.getInstance().log("No clients connected", 1);
                return;
            }

            // Broadcast the data package to all connected clients
            server.getBroadcastOperations().sendEvent("new_data", dataPackage);
        } catch (Exception e) {
            ConsoleLogger.getInstance().log("Exception thrown: " + e.getMessage(), 2);
        }
    }
}
