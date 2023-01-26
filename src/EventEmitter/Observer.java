package EventEmitter;

public abstract class Observer {
    protected EventEmitter eventEmitter;
    public abstract void update();
}