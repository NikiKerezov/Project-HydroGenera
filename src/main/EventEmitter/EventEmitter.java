package EventEmitter;

import EventEmitter.Contracts.IObserver;
import LocalData.Models.DataPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventEmitter {

    public static EventEmitter instance = new EventEmitter();
    private final Executor workQueue = Executors.newCachedThreadPool();

    private EventEmitter() {}

    public static EventEmitter getInstance() {
        return instance;
    }

    private final ArrayList<IObserver> observers = new ArrayList<IObserver>();
    private int state;

    private DataPackage dataPackage;

    public DataPackage getDataPackage() {
        return dataPackage;
    }

    public void setDataPackage(DataPackage dataPackage) {
        this.dataPackage = dataPackage;
    }

    public int getState() {
        return state;
    }

    public void setState(int state, DataPackage dataPackage) {
        this.state = state;
        notifyAllObservers(dataPackage);
    }

    public void attach(IObserver observer){
        observers.add(observer);
    }

    public void detach(IObserver observer){
        observers.remove(observer);
    }

    public void notifyAllObservers(DataPackage dataPackage){

        for (IObserver observer : observers) {
            workQueue.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        observer.update(dataPackage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
    }
}
