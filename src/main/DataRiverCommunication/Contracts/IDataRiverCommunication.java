package DataRiverCommunication.Contracts;

import EventEmitter.Contracts.IObserver;
import LocalData.Models.DataPackage;

import java.io.IOException;

public interface IDataRiverCommunication extends IObserver {
    void sendPackage(DataPackage dataPackage) throws IOException;
    void connect();
}
