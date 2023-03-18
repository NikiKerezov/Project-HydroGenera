package Processor.Contracts;

import java.io.IOException;

public interface IReadFromSerialPort {
    void ReadAndProcess() throws IOException;
}
