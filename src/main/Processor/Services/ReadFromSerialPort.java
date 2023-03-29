package Processor.Services;

import DependancyContainer.Models.UartSetting;
import EventEmitter.EventEmitter;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import Processor.Contracts.IProcessPackage;
import Processor.Contracts.IReadFromSerialPort;
import Processor.Utils.PrintDataPackage;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReadFromSerialPort implements IReadFromSerialPort {

    private static ReadFromSerialPort instance;
    private final IProcessPackage processPackage;
    private final PrintDataPackage printDataPackage;
    private final UartSetting uartSetting;
    private final ILogger logger;

    public static void setInstance(IProcessPackage processPackage, UartSetting uartSetting, PrintDataPackage printDataPackage, ILogger logger) throws Exception {
        instance = new ReadFromSerialPort(processPackage, uartSetting, printDataPackage, logger);
    }


    public static ReadFromSerialPort getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("ReadFromSerialPort is not initialized");
        }
        return instance;
    }

    private ReadFromSerialPort(IProcessPackage processPackage, UartSetting uartSetting, PrintDataPackage printDataPackage, ILogger logger) {
        this.processPackage = processPackage;
        this.uartSetting = uartSetting;
        this.printDataPackage = printDataPackage;
        this.logger = logger;
    }

    public void ReadAndProcess() throws IOException {

        SerialPort serialPort = SerialPort.getCommPort(uartSetting.getPort());

        serialPort.setComPortParameters(uartSetting.getBaudRate(),
                                        uartSetting.getDataBits(),
                                        uartSetting.getStopBits(),
                                        uartSetting.getParity());

        while (true) {
            serialPort.openPort();
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

            InputStream in = serialPort.getInputStream();

            if (serialPort.isOpen()) {
                logger.log("Port is open",1);
            }
            else {
                logger.log("Port is not open", 1);
            }
            ArrayList<Character> input = new ArrayList<Character>();

            logger.log("Opened port: " + serialPort.getSystemPortName(), 1);

            //clear buffer

            byte[] buffer = new byte[serialPort.bytesAvailable()];

            serialPort.readBytes(buffer, buffer.length);

            int flag = 0;

            while(true)
                try {
                    Character numRead = (char) in.read();
                    input.add(numRead);

                    if ((numRead == 0x0a && input.size() != 16 && flag == 0)) {
                        flag = 1;
                        input.clear();
                        logger.log("Clearing buffer", 2);
                        continue;
                    }

                    if (input.size() == 16 
                        && input.get(14) == 0x0d
                        && input.get(15) == 0x0a) {

                        logger.log("Went trough check", 3);
                        flag = 0;

                        DataPackage dataPackage = processPackage.processPackage(input);

                        EventEmitter.getInstance().setDataPackage(dataPackage);
                        EventEmitter.getInstance().notifyAllObservers(dataPackage);

                        logger.log(dataPackage.toString(), 3);
                    }

                    if (input.size() >= 16) {
                        logger.log("Clearing buffer", 2);
                        input.clear();
                    }

                } catch (Exception exception) {
                    logger.log("Exception throws: " + exception.getMessage(), 1);
                }
        }
    }
}
