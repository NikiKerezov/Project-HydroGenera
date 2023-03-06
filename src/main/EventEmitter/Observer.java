package EventEmitter;

import LocalData.Models.DataPackage;

import java.io.IOException;

public abstract class Observer {
    protected EventEmitter eventEmitter;
    public abstract void update(DataPackage dataPackage) throws IOException;
}