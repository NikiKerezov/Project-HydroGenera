package ServerCommunication.Contracts;

import LocalData.Models.DataPackage;

import java.io.IOException;

public interface IServerConnection {
    void sendPackage(DataPackage dataPackage) throws IOException;
}
