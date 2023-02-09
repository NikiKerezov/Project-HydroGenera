package ServerCommunication;

import LocalData.Models.DataPackage;
import ServerCommunication.Services.WebSocketConnection;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        WebSocketConnection wsc = new WebSocketConnection("ws://socket.statistics.green-innovation.bg");
        wsc.sendPackage(new DataPackage(4,"a",4,4, 5, 5, 5));
    }
}
