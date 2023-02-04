package EventEmitter;

import LocalData.Models.DataPackage;

public abstract class Observer {
    protected EventEmitter eventEmitter;
    public abstract void update(DataPackage dataPackage);
}