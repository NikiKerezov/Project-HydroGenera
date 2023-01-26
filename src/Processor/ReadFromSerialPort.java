package Processor;

import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.util.ArrayList;

public class ReadFromSerialPort {//naming, DI, singleton
    //initiate the serial port

    private static final ReadFromSerialPort instance = new ReadFromSerialPort();

    public static ReadFromSerialPort getInstance() {
        return instance;
    }

    private static final String PORT_NAME = "COM8";

    private ReadFromSerialPort() {
        //TODO: PORT_NAME = getPortName from settings etc
    }

    public static void readFromCom() {//?flush port

        SerialPort serialPort = SerialPort.getCommPort("COM3");
        //serialPort.setComPortParameters(9600, 8, 1, 0);

        SerialPort[] arrayOfSerialPort = SerialPort.getCommPorts();

        for (SerialPort port : arrayOfSerialPort) {
            System.out.println(port.getSystemPortName());
        }
/*
        try {
            serialPort
        } catch (Exception e) {
            throw new RuntimeException("Port is not open");
        }
*/
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

                    if (input.size() == 16 && input.get(14) == 0x0a && input.get(15) == 0x0d) {//?is it enough
                        //input[15] = 0a
                        //input[14] = 0d
                        System.out.println(CreateDataPackage.getInstance().createPackage(input).toString());
                        input.clear();
                    }

                } catch (Exception ignored) {}
                finally {
                    serialPort.closePort();
                }
        }
    }

}
