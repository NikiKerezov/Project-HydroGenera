package StopWatch.Contracts;

public interface IStopWatch {
    void start();
    void stop();
    long getElapsedTime();
    long getElapsedTimeFromStop();
    void reset();
}
