package DependancyContainer;

public interface IData {
    CommunicationProtocol getCommunicationProtocol();
    GeneratorSetting getGeneratorSetting();
    LogSettings getLogSettings();
    LocalDataSettings getLocalDataSettings();
    UartSetting getUartSetting();
    ServerSettings getServerSettings();
}
