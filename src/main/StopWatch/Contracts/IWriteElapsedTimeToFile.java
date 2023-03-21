package StopWatch.Contracts;
import java.io.IOException;

public interface IWriteElapsedTimeToFile {
    void WriteTimeToFile(long previousTime) throws IOException;
}
