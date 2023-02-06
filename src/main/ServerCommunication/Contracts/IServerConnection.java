package ServerCommunication.Contracts;

import LocalData.Models.DataPackage;

public interface IServerConnection {
    void sendPackage(DataPackage dataPackage);
}
