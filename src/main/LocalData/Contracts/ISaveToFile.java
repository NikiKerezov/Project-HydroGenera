package LocalData.Contracts;

import EventEmitter.Contracts.IObserver;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;

public interface ISaveToFile extends IObserver {
    public void saveToFile(DataPackage dataPackage);
}
