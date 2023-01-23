import com.fazecast.jSerialComm.SerialPort;
import readFromCom.ReadFromCom;
import dataPackage.DataPackage;
import createFile.CreateFile;
import java.io.IOException;
import java.io.InputStream;


public class Main {
    public static void main(String[] args) {
        Main m = new Main();

        ReadFromCom.readFromCom();
    }

    private void demo() {
        DataPackage dataPackage = new DataPackage(20.0, 15.0, 32.0, 10.0, 4.0);

        CreateFile.setPathFiles("D:\\COMPortData\\");

        CreateFile.saveDataPackageToCSV(dataPackage);
    }
}
