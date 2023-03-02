package WebServer;

import LocalData.Models.DataPackage;
import Logger.Services.ConsoleLogger;
import WebServer.Contracts.IWebServer;
import WebServer.Services.WebServer;

public class Main {
    public static void main(String[] args) throws Exception {
        // Get the logger instance
        ConsoleLogger.setInstance(3);
        ConsoleLogger logger = ConsoleLogger.getInstance();
        WebServer server = WebServer.getInstance(logger);
        server.startServer();

            // Create a data package to send
            DataPackage dataPackage = new DataPackage(10, "nz", 1, 2, 3, 4, 5);

            // Send the data package
            server.sendPackage(dataPackage);

    }
}
