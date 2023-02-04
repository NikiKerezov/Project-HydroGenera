package ServerCommunication.Service;

import LocalData.Models.DataPackage;

public interface IServerConnection {
    void sendPackage(DataPackage dataPackage);
}
