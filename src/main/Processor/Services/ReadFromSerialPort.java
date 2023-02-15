package Processor.Services;

import DependancyContainer.Models.UartSetting;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Logger.Contracts.ILogger;
import Logger.Services.ConsoleLogger;
import Processor.Contracts.IProcessPackage;
import Processor.Utils.PrintDataPackage;
import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadFromSerialPort extends Observer {

    private static ReadFromSerialPort instance;

    private IProcessPackage processPackage;
    private PrintDataPackage printDataPackage;
    private UartSetting uartSetting;

    private final ILogger logger = ConsoleLogger.getInstance(); //TODO: get from settings

    public static void setInstance(ProcessPackage processPackage,  UartSetting uartSetting, PrintDataPackage printDataPackage){
        instance = new ReadFromSerialPort(processPackage, uartSetting, printDataPackage);
    }

    public static ReadFromSerialPort getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("ReadFromSerialPort is not initialized");
        }
        return instance;
    }

    private ReadFromSerialPort(ProcessPackage processPackage, UartSetting uartSetting, PrintDataPackage printDataPackage) {
        this.processPackage = processPackage;
        this.uartSetting = uartSetting;
        this.printDataPackage = printDataPackage;
    }

    public void ReadAndProcess() {

        SerialPort serialPort = SerialPort.getCommPort(uartSetting.getPort());

        serialPort.setComPortParameters(uartSetting.getBaudRate(),
                                        uartSetting.getDataBits(),
                                        uartSetting.getStopBits(),
                                        uartSetting.getParity());

        while (true) {
            serialPort.openPort();

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
                        System.out.println("Clearing input");
                        continue;
                    }

                    if (input.size() == 16 
                        && input.get(14) == 0x0d
                        && input.get(15) == 0x0a) {

                        flag = 0;

                        DataPackage dataPackage = processPackage.processPackage(input);

                        //EventEmitter.getInstance().setDataPackage(dataPackage);
                        //EventEmitter.getInstance().notifyAllObservers(dataPackage);

                        printDataPackage.printPackage(dataPackage);

                        input.clear();
                    }

                    if (input.size() => 16) {
                        input.clear();
                    }

                } catch (Exception exception) {
                    //logger.log("Exception throws: " + exception.getMessage(), 1);
                }
        }
    }

    @Override
    public void update(DataPackage dataPackage) {
        ReadAndProcess();
    }
}
