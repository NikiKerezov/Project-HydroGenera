package EventEmitter;

import EventEmitter.Contracts.IObserver;
import LocalData.Models.DataPackage;

import java.io.IOException;

public abstract class Observer implements IObserver {
    protected EventEmitter eventEmitter;
    public abstract void update(DataPackage dataPackage) throws IOException;
}