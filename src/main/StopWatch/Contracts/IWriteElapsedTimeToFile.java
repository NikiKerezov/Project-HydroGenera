package StopWatch.Contracts;
import java.io.IOException;

public interface IWriteElapsedTimeToFile {
    void WriteTimeToFile(IStopWatch elapsedTime, IStopWatch overallTime) throws IOException;
}
