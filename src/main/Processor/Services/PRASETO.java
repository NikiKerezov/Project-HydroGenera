package Processor.Services;

import LocalData.Models.DataPackage;
import Processor.Contracts.IProcessPackage;

import java.util.ArrayList;

public class PRASETO implements IProcessPackage {
    private static final PRASETO instance = new PRASETO();

    private PRASETO() {}

    public static PRASETO getInstance() {
        return instance;
    }

    private boolean validateData(ArrayList<Character> data) {
        if (data.size() != 16) {
            return false;
        }

        double sum = 0;
        for (int i = 1; i < 14; i++) {
            sum += data.get(i);
        }

        sum %= 256;

        if (data.get(0) != sum) {
            return false;
        }
        return true;
    }
    public DataPackage processPackage(ArrayList<Character> data) {//to be an external class
        if (!validateData(data)) {
            throw new IllegalArgumentException("Invalid data");
        }

        //Add address

        int address = data.get(1);
        address -= 48;

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

        double amp;

        amp = temp / 4;

        //Getting Temperature

        double tmp = data.get(0);

        String timestamp = String.valueOf(System.currentTimeMillis());

        return new DataPackage(address, timestamp, amp, tmp, pwm, bar, uin);
    }
}
