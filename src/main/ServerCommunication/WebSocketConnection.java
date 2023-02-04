package ServerCommunication;

import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class WebSocketConnection extends Observer implements IServerConnection {

    final Socket socket;

    public WebSocketConnection() throws URISyntaxException {
        socket = IO.socket("ws://socket.statistics.green-innovation.bg");
        socket.open();
    }

    @Override
    public void sendPackage(DataPackage dataPackage) {
        socket.emit("data", dataPackage);
    }

    @Override
    public void update(DataPackage dataPackage) {
        sendPackage(dataPackage);
    }
}
