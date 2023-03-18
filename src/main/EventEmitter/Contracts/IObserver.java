package EventEmitter.Contracts;

import EventEmitter.EventEmitter;
import LocalData.Models.DataPackage;

import java.io.IOException;

public interface IObserver {
    void update(DataPackage dataPackage) throws IOException;
}
