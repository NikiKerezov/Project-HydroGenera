package dataPackage;

public class DataPackage {
    private double amp, tmp, bar, uin, pwm;
    public DataPackage(double amp, double tmp, double pwm, double bar, double uin) {
        this.amp = amp;
        this.tmp = tmp;
        this.bar = bar;
        this.uin = uin;
        this.pwm = pwm;
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

    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getUin()).append(",").append(this.getTmp()).append(",").append(this.getAmp()).append(",").append(this.getBar()).append(",").append(this.getPwm()).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "DataPackage{" +
                "amp=" + amp +
                ", tmp=" + tmp +
                ", bar=" + bar +
                ", uin=" + uin +
                ", pwm=" + pwm +
                '}';
    }
}
