package Processor.Services;

import LocalData.Models.DataPackage;
import Processor.Contracts.IProcessPackage;
import UpTime.Services.UpTime;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProcessPackageBoardVersionGI2CPU28 implements IProcessPackage {

    private static final ProcessPackageBoardVersionGI2CPU28 instance = new ProcessPackageBoardVersionGI2CPU28();

    private ProcessPackageBoardVersionGI2CPU28() {}

    public static ProcessPackageBoardVersionGI2CPU28 getInstance() {
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

        DecimalFormat dfTwoDec = new DecimalFormat("#.##");
        DecimalFormat dfThreeDec = new DecimalFormat("#.###");
        String format;

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
        pwmHi = 65535 - pwmHi;
        data.remove(0);

        //Getting PWM low

        int pwmLow = data.get(0);
        data.remove(0);
        pwmLow = pwmLow << 8;
        pwmLow = pwmLow | data.get(0);
        pwmLow = 65535 - pwmLow;
        data.remove(0);

        //Calculating PWM

        double pwm = ((double) pwmHi / (double) (pwmHi + pwmLow)) * 100;
        format = dfTwoDec.format(pwm);
        pwm = Double.parseDouble(format);

        //Getting Uin

        int temp = data.get(0);
        data.remove(0);
        temp = temp << 8;
        temp = temp | data.get(0);
        data.remove(0);

        //Calculating Uin

        double uin = temp * 0.0048828125 * 10.2338;
        format = dfTwoDec.format(uin);
        uin = Double.parseDouble(format);

        //Getting Bar

        temp = data.get(0);
        data.remove(0);
        temp = temp << 8;
        temp = temp | data.get(0);
        data.remove(0);

        //Calculating Bar

        double bar = (temp * 0.0048828125 - 0.916) / 1.2213333;
        format = dfThreeDec.format(bar);
        bar = Double.parseDouble(format);

        //Getting Amp

        temp = data.get(0);
        data.remove(0);
        temp = temp << 8;
        temp = temp | data.get(0);
        data.remove(0);

        //Calculating Amp

        double amp;

        amp = (temp  * 0.0048828125 - 0.5859375) / 0.0390625;
        format = dfTwoDec.format(amp);
        amp = Double.parseDouble(format);

        //Getting Temperature

        double tmp = data.get(0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.now();
        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();

        return new DataPackage(address, timestamp.toString(), amp, tmp, pwm, bar, uin, Long.toString(mxBean.getUptime()/1000), Long.toString(mxBean.getUptime()/1000 + UpTime.getInstance().getPreviousTime()));
    }
}
