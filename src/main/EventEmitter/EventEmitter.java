package EventEmitter;

import LocalData.Models.DataPackage;

import java.util.ArrayList;

public class EventEmitter {

    public static EventEmitter instance = new EventEmitter();

    private EventEmitter() {}

    public static EventEmitter getInstance() {
        return instance;
    }

    private final ArrayList<Observer> observers = new ArrayList<Observer>();
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

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void detach(Observer observer){
        observers.remove(observer);
    }

    public void notifyAllObservers(DataPackage dataPackage){
        for (Observer observer : observers) {
            observer.update(dataPackage);
        }
    }
}
