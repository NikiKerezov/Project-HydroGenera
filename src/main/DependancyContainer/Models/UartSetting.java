package DependancyContainer.Models;

public class UartSetting {
    private String port;
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int parity;

    public UartSetting() {
        // Default constructor required by Jackson
    }

//    public UartSetting(String port, int baudRate, int dataBits, int stopBits, int parity) {
//        this.port = port;
//        this.baudRate = baudRate;
//        this.dataBits = dataBits;
//        this.stopBits = stopBits;
//        this.parity = parity;
//    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }
}
