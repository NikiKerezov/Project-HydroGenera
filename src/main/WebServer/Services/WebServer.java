package WebServer.Services;

import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import WebServer.Contracts.IWebServer;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import java.net.URISyntaxException;

public class WebServer extends Observer implements IWebServer {

    private static WebServer instance;

    private SocketIOServer server;
    private WebServer() throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        server = new SocketIOServer(config);
    }
    public void startServer(){
        server.start();
        server.addEventListener("message", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                // broadcast messages to all clients
                //server.getBroadcastOperations().sendEvent("message", data);
            }
        });

        server.addConnectListener(new ConnectListener(){

            @Override
            public void onConnect(SocketIOClient socketIOClient) {

            }
        });
    }
    public static void setInstance() throws URISyntaxException, InterruptedException {
        instance = new WebServer();
    }

    public static WebServer getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("instance was forgotten to be initialized");
        }
        return instance;
    }

    @Override
    public void update(DataPackage dataPackage) {

    }

    @Override
    public void sendPackage(DataPackage dataPackage) {

    }
}
