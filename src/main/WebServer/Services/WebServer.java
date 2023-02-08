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

    public WebServer(String url) throws InterruptedException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);
        final SocketIOServer server = new SocketIOServer(config);
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
        server.start();

        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }

    public static void setInstance(String url) throws URISyntaxException, InterruptedException {
        instance = new WebServer(url);
    }

    public static WebServer getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("instance was forgotten to be initialized");
        }
        return instance;
    }


    public static void main(String[] args) throws InterruptedException {

    }

    @Override
    public void update(DataPackage dataPackage) {

    }

    @Override
    public void sendPackage(DataPackage dataPackage) {

    }
}
