package LocalData.Models;

public class DataPackage {
    private double amp, tmp, bar, uin, pwm;
    private int adr;
    private String timestamp;

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    private String uptime;

    public DataPackage(int adr, String timestamp, double amp, double tmp, double pwm, double bar, double uin, String uptime) {
        //this.id = settings.getBoardId
        this.adr = adr;
        this.timestamp = timestamp;
        this.amp = amp;
        this.tmp = tmp;
        this.bar = bar;
        this.uin = uin;
        this.pwm = pwm;
        this.uptime = uptime;
    }
    public int getAdr() {
        return adr;
    }

    public void setAdr(int adr) {
        this.adr = adr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmp() {
        return amp;
    }

    public void setAmp(double amp) {
        this.amp = amp;
    }

    public double getTmp() {
        return tmp;
    }

    public void setTmp(double tmp) {
        this.tmp = tmp;
    }

    public double getBar() {
        return bar;
    }

    public void setBar(double bar) {
        this.bar = bar;
    }

    public double getUin() {
        return uin;
    }

    public void setUin(double uin) {
        this.uin = uin;
    }

    public double getPwm() {
        return pwm;
    }

    public void setPwm(double pwm) {
        this.pwm = pwm;
    }
}
