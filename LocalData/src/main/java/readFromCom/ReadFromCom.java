package readFromCom;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import dataPackage.DataPackage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ReadFromCom {
    //initiate the serial port
    private static final String PORT_NAME = "COM7";

    static SerialPort comPort = SerialPort.getCommPort(PORT_NAME);

    private static boolean validateData(ArrayList<Character> data) {
        if (data.size() != 16) {
            return false;
        }
        //TODO: validate data
        return true;
    }

    private static DataPackage createPackage(ArrayList<Character> data) {
        if (!validateData(data)) {
            throw new IllegalArgumentException("Invalid data");
        }
        //Removing bytes 1 2 and 3
        data.remove(0);
        data.remove(0);
        data.remove(0);

        //Getting PWM hi

        int pwmHi = data.get(0);
        data.remove(0);
        pwmHi = pwmHi << 8;
        pwmHi = pwmHi | data.get(0);
        data.remove(0);

        //Getting PWM low

        int pwmLow = data.get(0);
        data.remove(0);
        pwmLow = pwmLow << 8;
        pwmLow = pwmLow | data.get(0);
        data.remove(0);

        //Calculating PWM

        double pwm = (pwmHi / (pwmHi + pwmLow)) * 100;

        //Getting Uin

        int temp = data.get(0);
        data.remove(0);
        temp = temp << 8;
        temp = temp | data.get(0);
        data.remove(0);

        //Calculating Uin

        double uin = temp * 0.0048828125 * 10.2338;

        //Getting Bar

        temp = data.get(0);
        data.remove(0);
        temp = temp << 8;
        temp = temp | data.get(0);
        data.remove(0);

        //Calculating Bar

        double bar = (temp * 0.0048828125 - 0.916) / 1.2213333;

        //Getting Amp

        temp = data.get(0);
        data.remove(0);
        temp = temp << 8;
        temp = temp | data.get(0);
        data.remove(0);

        //Calculating Amp

        double amp = (temp  * 0.0048828125 - 0.5859375) / 0.0390625;

        //Getting Temp

        double tmp = data.get(0);

        return new DataPackage(amp, tmp, pwm, bar, uin);
    }

    public static void readFromCom() {
        while (true) {
            comPort.openPort();
            InputStream in = comPort.getInputStream();

            if (comPort.isOpen()) {
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
                    if (input.size() == 16) {
                        System.out.println(createPackage(input).toString());
                        input.clear();
                    }

                } catch (Exception ignored) {}

        }
    }

}
