package Processor.Services;

import DependancyContainer.Models.UartSetting;
import EventEmitter.EventEmitter;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import Processor.Contracts.IProcessPackage;
import Processor.Utils.PrintDataPackage;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ReadFromSerialPort {

    private static ReadFromSerialPort instance;
    private final IProcessPackage processPackage;
    private final PrintDataPackage printDataPackage;
    private final UartSetting uartSetting;
    private final ILogger logger = ConsoleLogger.getInstance(); //TODO: get from settings

    public static void setInstance(ProcessPackageBoardVersionGI2CPU28 processPackage, UartSetting uartSetting, PrintDataPackage printDataPackage){
        instance = new ReadFromSerialPort(processPackage, uartSetting, printDataPackage);
    }


    public static ReadFromSerialPort getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("ReadFromSerialPort is not initialized");
        }
        return instance;
    }

    private ReadFromSerialPort(IProcessPackage processPackage, UartSetting uartSetting, PrintDataPackage printDataPackage) {
        this.processPackage = processPackage;
        this.uartSetting = uartSetting;
        this.printDataPackage = printDataPackage;
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
                System.out.println("Port is open");
            }
            else {
                throw new RuntimeException("Port is not open");
            }
            ArrayList<Character> input = new ArrayList<>();

            System.out.println("Opened port: " + serialPort.getSystemPortName());

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
                        logger.log("Clearing buffer", 3);
                        continue;
                    }

                    if (input.size() == 16 
                        && input.get(14) == 0x0d
                        && input.get(15) == 0x0a) {

                        flag = 0;

                        DataPackage dataPackage = processPackage.processPackage(input);

                        EventEmitter.getInstance().setDataPackage(dataPackage);
                        EventEmitter.getInstance().notifyAllObservers(dataPackage);

                        printDataPackage.printPackage(dataPackage);
                    }

                    if (input.size() >= 16) {
                        input.clear();
                    }

                } catch (Exception exception) {
                    logger.log("Exception throws: " + exception.getMessage(), 1);
                }
        }
    }
}
