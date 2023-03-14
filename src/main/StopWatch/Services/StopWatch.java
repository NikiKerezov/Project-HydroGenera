package StopWatch.Services;

import StopWatch.Contracts.IStopWatch;

public class StopWatch implements IStopWatch {
    private long startTime;
    private long endTime;
    public StopWatch() {
        this.startTime = 0;
        this.endTime = 0;
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        this.endTime = System.currentTimeMillis();
    }

    @Override
    public long getElapsedTime() {
        return (System.currentTimeMillis() - this.endTime) / 1000;
    }

    @Override
    public long getElapsedTimeFromStop() {
        return (this.endTime - this.startTime) / 1000;
    }

    @Override
    public void reset() {
        this.startTime = 0;
        this.endTime = 0;
    }
}
