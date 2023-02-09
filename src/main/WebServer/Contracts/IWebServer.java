package WebServer.Contracts;

import LocalData.Models.DataPackage;

public interface IWebServer {
    void sendPackage(DataPackage dataPackage);
}
