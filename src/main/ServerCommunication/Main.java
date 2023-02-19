package ServerCommunication;

import LocalData.Models.DataPackage;
import Logger.Services.ConsoleLogger;
import ServerCommunication.Services.WebSocketConnection;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws Exception {
        WebSocketConnection.getInstance("ws://socket.statistics.green-innovation.bg",
                ConsoleLogger.instance).sendPackage(new DataPackage(4,"a",4,4, 5, 5, 5));
    }
}
