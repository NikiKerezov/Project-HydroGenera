package DependancyContainer.Models;

public class LogSettings {
    private String logType;
    private int logLevel;
    private String logPath;

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
    public String getLogPath() {
        return logPath;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }
}
