package Processor.Services;

import EventEmitter.EventEmitter;
import EventEmitter.Observer;
import LocalData.Models.DataPackage;
import Processor.Contracts.IProcessPackage;
import Processor.Utils.printDataPackage;
import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadFromSerialPort {

    private static ReadFromSerialPort instance;

    private IProcessPackage processPackage;
    private printDataPackage printDataPackage;
    private String PORT_NAME;

    public static void setInstance(IProcessPackage processPackage, String port){
        instance = new ReadFromSerialPort(processPackage, port);
    }

    public static ReadFromSerialPort getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("ReadFromSerialPort is not initialized");
        }
        return instance;
    }

    private ReadFromSerialPort(IProcessPackage processPackage, String port) {
        this.processPackage = processPackage;
        this.PORT_NAME = port;
    }

    public void ReadAndProcess() {//?flush port

        //TODO: PORT_NAME = getPortName from settings etc

        SerialPort serialPort = SerialPort.getCommPort(PORT_NAME);
        serialPort.setComPortParameters(9600, 8, 1, 0);

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
                        && input.get(14) == 0x0a 
                        && input.get(15) == 0x0d) {
                            
                        //input[15] = 0a
                        //input[14] = 0d

                        DataPackage dataPackage = processPackage.processPackage(input);
                        EventEmitter.getInstance().setDataPackage(dataPackage);
                        EventEmitter.getInstance().notifyAllObservers(dataPackage);
                        
                        input.clear();
                    }

                } catch (Exception ignored) {}
                finally {
                    serialPort.closePort();
                }
        }
    }
}
