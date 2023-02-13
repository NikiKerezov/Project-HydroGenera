package Processor.Services;

import DependancyContainer.Models.UartSetting;
import EventEmitter.EventEmitter;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Logger.Services.ConsoleLogger;
import Processor.Contracts.IProcessPackage;
import Processor.Utils.printDataPackage;
import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadFromSerialPort extends Observer {

    private static ReadFromSerialPort instance;

    private IProcessPackage processPackage;
    private printDataPackage printDataPackage;
    private UartSetting uartSetting;

    public static void setInstance(IProcessPackage processPackage, UartSetting uartSetting){
        instance = new ReadFromSerialPort(processPackage, uartSetting);
    }

    public static ReadFromSerialPort getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("ReadFromSerialPort is not initialized");
        }
        return instance;
    }

    private ReadFromSerialPort(IProcessPackage processPackage, UartSetting uartSetting) {
        this.processPackage = processPackage;
        this.uartSetting = uartSetting;
    }

    public void ReadAndProcess() {//?flush port

        SerialPort serialPort = SerialPort.getCommPort(uartSetting.getPort());

        serialPort.setComPortParameters(uartSetting.getBaudRate(),
                                        uartSetting.getDataBits(),
                                        uartSetting.getStopBits(),
                                        uartSetting.getParity());

        SerialPort[] arrayOfSerialPort = SerialPort.getCommPorts();

        for (SerialPort port : arrayOfSerialPort) {
            System.out.println(port.getSystemPortName());
        }

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
            while(true)
                try {
                    Character numRead = (char) in.read();
                    input.add(numRead);

                    if (input.size() == 16 
                        && input.get(14) == 0x0d
                        && input.get(15) == 0x0a) {

                        DataPackage dataPackage = processPackage.processPackage(input);
                        EventEmitter.getInstance().setDataPackage(dataPackage);
                        EventEmitter.getInstance().notifyAllObservers(dataPackage);

                        input.clear();
                    }

                    input.clear();

                } catch (Exception ignored) {}
        }
    }

    @Override
    public void update(DataPackage dataPackage) {
        ReadAndProcess();
    }
}
