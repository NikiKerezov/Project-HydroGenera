package JsonToObject;

public class Data implements IData{
    private CommunicationProtocol communicationProtocol;
    private GeneratorSetting generatorSetting;
    private LogSettings logSettings;
    private LocalDataSettings localDataSettings;
    private UartSetting uartSetting;
    private ServerSettings serverSettings;

    public CommunicationProtocol getCommunicationProtocol() {
        return communicationProtocol;
    }

    public void setCommunicationProtocol(CommunicationProtocol communicationProtocol) {
        this.communicationProtocol = communicationProtocol;
    }

    public GeneratorSetting getGeneratorSetting() {
        return generatorSetting;
    }

    public void setGeneratorSetting(GeneratorSetting generatorSetting) {
        this.generatorSetting = generatorSetting;
    }

    public LogSettings getLogSettings() {
        return logSettings;
    }

    public void setLogSettings(LogSettings logSettings) {
        this.logSettings = logSettings;
    }

    public LocalDataSettings getLocalDataSettings() {
        return localDataSettings;
    }

    public void setLocalDataSettings(LocalDataSettings localDataSettings) {
        this.localDataSettings = localDataSettings;
    }

    public UartSetting getUartSetting() {
        return uartSetting;
    }

    public void setUartSetting(UartSetting uartSetting) {
        this.uartSetting = uartSetting;
    }

    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    public void setServerSettings(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }
}
